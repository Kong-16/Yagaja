<script setup lang="ts">
import Draggable from 'zhyswan-vuedraggable';
import HideableEdgeContent from '@/components/HideableEdgeContent.vue';
import PlanEditor from '@/components/PlanEditor.vue';
import ReviewView from './ReviewView.vue';
import TripPopup from '@/components/TripPopup.vue';
import TripSearchCard from '@/components/TripSearchCard.vue';
import LRoutingMachine from '@/components/LRoutingMachine.vue';
import { LMap, LMarker, LPopup, LTileLayer } from '@vue-leaflet/vue-leaflet';
import { PlanDto } from '../apis/plans';
import { TripDto, TripSearchQuery } from '../apis/trips';
import { fetchTrips } from '@/apis/trips';
import { ref, onBeforeMount, onUnmounted, watch } from 'vue';
import { computed } from '@vue/reactivity';

const map = ref<any>(null); // eslint-disable-line
const mapObject = ref<any>(null); // eslint-disable-line
const markers = ref<any>(null); // eslint-disable-line
const mapCenter = ref([35.19973626959668, 126.80795214530028]);
const zoomLevel = ref(7);
const trips = ref<TripDto[] | null>(null);
const plan = ref<PlanDto | null>(null);
const tripToViewReview = ref<TripDto>();
const showReview = ref(false);

const waypoints = computed(() => plan.value?.subplans.map((sp) => sp.waypoints.map(({ trip }) => trip)).flat());
const waypointLatLngs = ref();
watch(
  () => waypoints.value,
  () => {
    waypointLatLngs.value = waypoints.value?.map((wp) => ({ lat: wp.latitude, lng: wp.longitude }));
  }
);
watch(
  () => [map.value, map.value?.leafletObject],
  () => {
    mapObject.value = map.value?.leafletObject ?? null;
  }
);

function openTripReview(tripInfo: TripDto) {
  tripToViewReview.value = tripInfo;
  showReview.value = true;
}

function focusTripMarker(tripIndex: number) {
  if (map.value !== null && trips.value !== null) {
    markers.value[tripIndex].leafletObject.openPopup();
    const { latitude: lat, longitude: lng } = trips.value[tripIndex];
    map.value.leafletObject.panTo([lat, lng]);
  }
}

async function handleTripSearchSubmit({ detail: tripSearchQuery }: { detail: TripSearchQuery }) {
  trips.value = await fetchTrips(tripSearchQuery);
}

function asUniqueIndexPair(trip: TripDto): { index: number; trip: TripDto } {
  return { index: Date.now(), trip };
}

function handleWaypointClick(e: MouseEvent, trip: TripDto) {
  openTripReview(trip);
}

function handlePlanSave(plan: PlanDto) {
  const simplifiedPlan: { name: string; subplans: any[] } = { name: plan.name, subplans: [] };  // eslint-disable-line
  for (let i = 0; i < plan.subplans.length; ++i) {
    simplifiedPlan.subplans.push({ ...plan.subplans[i] });
    simplifiedPlan.subplans[i].waypoints = plan.subplans[i].waypoints.map(({ trip }) => trip.contentId);
  }
  console.log(simplifiedPlan);
  fetch(`/plans/add`,{
        method : 'POST',
        headers :{
          'Content-Type': 'application/json;charset=utf-8',
        },
        body : JSON.stringify(simplifiedPlan),
      }
  )
}


onBeforeMount(() => {
  self.addEventListener('searchsubmit', handleTripSearchSubmit as any); // eslint-disable-line
});

onUnmounted(() => {
  self.removeEventListener('searchsubmit', handleTripSearchSubmit as any); // eslint-disable-line
});
</script>

<template>
  <div class="w-100 h-100" :class="{ hideLeafletRoutingMachine: trips !== null || (waypoints?.length ?? 0) <= 1 }">
    <!-- 여행 계획창 -->
    <div class="plan-editor-container">
      <div class="plan-editor-wrapper">
        <plan-editor v-model:plan="plan" @waypoint-click="handleWaypointClick" @save="handlePlanSave"></plan-editor>
      </div>
    </div>

    <!-- 여행지 검색 결과 목록 헤더 -->
    <div class="trip-search-result-list-header-container" v-if="trips !== null">
      <div class="trip-search-result-list-header">
        <img src="@/assets/svg/search.svg" alt="search result icon" />
        <span>{{ trips.length }}개의 검색 결과가 있어요.</span>
        <img src="@/assets/svg/close.svg" alt="search clear button icon" @click="trips = null" />
      </div>
    </div>

    <!-- 여행지 검색 결과 목록 -->
    <div class="trip-search-result-list" v-if="trips !== null && 0 < trips.length">
      <Draggable
        class="row g-2 dragArea"
        :list="trips"
        item-key="contentId"
        :group="{ name: 'trips', pull: 'clone', put: false }"
        :clone="asUniqueIndexPair"
      >
        <template #item="{ element, index }">
          <div class="col-12" @click="focusTripMarker(parseInt(index))">
            <trip-search-card :trip="element" @title-click.stop="openTripReview(element)"></trip-search-card>
          </div>
        </template>
      </Draggable>
    </div>

    <!-- 지도 -->
    <l-map ref="map" v-model:zoom="zoomLevel" v-model:center="mapCenter">
      <l-tile-layer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        attribution="© OpenStreetMap contributors"
        layer-type="base"
        name="OpenStreetMap"
      >
      </l-tile-layer>

      <l-marker ref="markers" v-for="(trip, i) in trips" :key="i" :lat-lng="[trip.latitude, trip.longitude]">
        <l-popup>
          <trip-popup
            :trip="trip"
            :draggable-group="{ name: 'trips', pull: 'clone', put: false }"
            :draggable-clone="asUniqueIndexPair"
            @click.stop="openTripReview(trip)"
          ></trip-popup>
        </l-popup>
      </l-marker>

      <template v-if="trips === null">
        <l-marker v-for="(trip, i) in waypoints" :key="i" :lat-lng="[trip.latitude, trip.longitude]"> </l-marker>
      </template>
    </l-map>

    <l-routing-machine
      :map-object="mapObject"
      :waypoints="waypointLatLngs"
      :line-options="{ addWaypoints: false }"
      :visible="trips === null && 1 < (waypoints?.length ?? 0)"
    ></l-routing-machine>
  </div>

  <Teleport to="#app">
    <hideable-edge-content v-model:show="showReview" width="600px" scrollable="true">
      <review-view :default="tripToViewReview"> </review-view>
    </hideable-edge-content>
  </Teleport>
</template>

<style>
/* Leaflet 마커 팝업 margin 제거 */
.leaflet-popup-content {
  margin: 0 !important;
}

.plan-editor-container {
  position: absolute;
  z-index: 5000;
  right: 0;

  border-radius: 12px;
  padding: 12px 12px;
  padding-left: 0px !important;
  width: 340px;
  height: 100%;
}

.plan-editor-wrapper {
  box-shadow: var(--color-gray-2) 0 0 11px 1px;
  border-radius: 12px;
  padding: 12px;
  width: 100%;
  height: 100%;

  background: white;

  -ms-overflow-style: none;
  scrollbar-width: none;
}

.trip-search-result-list-header-container {
  position: absolute;
  z-index: 5000;
  right: 340px;

  padding: 12px;
  width: 340px;
  height: 36px;
}

.trip-search-result-list-header {
  display: flex;
  gap: 12px;
  align-items: center;

  padding: 0 12px;
  height: 36px;

  background: white;
  border-radius: 12px;
}

.trip-search-result-list-header img {
  height: 60%;
}

/* 검색 결과 닫기 버튼 */
.trip-search-result-list-header *:last-child {
  cursor: pointer;
  margin-left: auto;
  border-radius: 100%;

  background: white;
  transition: background 0.2s;
}

.trip-search-result-list-header *:last-child:hover {
  background: var(--color-gray-0);
}

.trip-search-result-list {
  position: absolute;
  z-index: 5000;
  top: 48px;
  right: 340px;

  margin-top: 12px;
  padding: 12px;
  padding-top: 0 !important;
  width: 340px;
  height: calc(100% - 60px);

  overflow-y: scroll;

  -ms-overflow-style: none;
  scrollbar-width: none;
}

.plan-editor-wrapper::-webkit-scrollbar,
.trip-search-result-list::-webkit-scrollbar {
  display: none;
}
</style>

<style>
.leaflet-routing-container {
  margin-right: 352px !important;
}

.hideLeafletRoutingMachine .leaflet-routing-container {
  display: none !important;
}
</style>
