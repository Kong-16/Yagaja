<script setup lang="ts">
import HideableEdgeContent from '@/components/HideableEdgeContent.vue';
import TripPopup from '@/components/TripPopup.vue';
import TripSearchCard from '@/components/TripSearchCard.vue';
import { LMap, LMarker, LPopup, LTileLayer } from '@vue-leaflet/vue-leaflet';
import { TripInfo } from '@/types';
import { ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';
import ReviewView from './ReviewView.vue';

const $route = useRoute();
const map = ref<any>(null);  // eslint-disable-line
const markers = ref<any>(null);  // eslint-disable-line
const trips = ref<TripInfo[]>([]);
const zoomLevel = ref(7);

const tripToViewReview = ref<TripInfo>();
const showReview = ref(false);

function openTripReview(e: Event, tripInfo: TripInfo) {
    tripToViewReview.value = tripInfo;
    showReview.value = true;
}

function focusTripMarker(tripIndex: number) {
    if (map.value !== null) {
        markers.value[tripIndex].leafletObject.openPopup();
        const { latitude: lat, longitude: lng } = trips.value[tripIndex];
        map.value.leafletObject.panTo([lat, lng]);
    }
}

watchEffect(async () => {
    const queryString = Object.entries($route.query).map(([k, v]) => `${k}=${v}`).join('&');
    const resp = await fetch('/attractions' + (queryString ? `?${queryString}` : ''));
    if (resp.ok) {
        const json = await resp.json() as TripInfo[];
        trips.value = json;
    }
});
</script>

<template>
  <div class="w-100 h-100">
    <div class="trip-info-list">
      <div class="row g-2">
        <div class="col-12" v-for="(tripInfo, i) in trips" :key="i" @click="() => focusTripMarker(i)">
          <trip-search-card :trip-info="tripInfo" @title-click.stop="openTripReview"></trip-search-card>
        </div>
      </div>
    </div>

    <l-map ref="map" v-model:zoom="zoomLevel" :center="[35.19973626959668, 126.80795214530028]">
      <l-tile-layer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" layer-type="base" name="OpenStreetMap">
      </l-tile-layer>

      <l-marker ref="markers" v-for="(tripInfo, i) in trips" :key="i" :lat-lng="[tripInfo.latitude, tripInfo.longitude]">
        <LPopup>
          <trip-popup :trip-info="tripInfo"></trip-popup>
        </LPopup>
      </l-marker>
    </l-map>
  </div>

  <Teleport to="#app">
    <hideable-edge-content v-model:show="showReview" width="600px" scrollable="true">
      <review-view :trip="tripToViewReview">

      </review-view>
    </hideable-edge-content>
  </Teleport>
</template>

<style>
/* Leaflet 마커 팝업 margin 제거 */
.leaflet-popup-content {
    margin: 0 !important;
}

.trip-info-list {
    position: absolute;
    overflow-y: scroll;
    z-index: 5000;
    right: 0;
    width: 400px;
    height: 100%;
    padding: 12px;
}
</style>
