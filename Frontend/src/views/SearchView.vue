<script setup lang="ts">
import Draggable from 'zhyswan-vuedraggable';
import HideableEdgeContent from '@/components/HideableEdgeContent.vue';
import ReviewView from './ReviewView.vue';
import TripPopup from '@/components/TripPopup.vue';
import TripSearchCard from '@/components/TripSearchCard.vue';
import { LMap, LMarker, LPopup, LTileLayer } from '@vue-leaflet/vue-leaflet';
import { TripDto } from '../apis/trips';
import { ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';
import { fetchTrips } from '@/apis/trips';

const route = useRoute();
const map = ref<any>(null);  // eslint-disable-line
const mapCenter = ref([35.19973626959668, 126.80795214530028]);
const zoomLevel = ref(7);
const markers = ref<any>(null);  // eslint-disable-line
const trips = ref<TripDto[]>([]);

const reviewTargetTrip = ref<TripDto>();
const showReview = ref(false);

function openTripReview(trip: TripDto) {
  reviewTargetTrip.value = trip;
  showReview.value = true;
}

function focusTripMarker(tripIndex: number) {
  if (map.value !== null) {
    markers.value[tripIndex].leafletObject.openPopup();
    const { latitude: lat, longitude: lng } = trips.value[tripIndex];
    map.value.leafletObject.panTo([lat, lng]);
  }
}

watchEffect(() => {
  fetchTrips(route.query).then(fetchedTrips => trips.value = fetchedTrips);
});
</script>

<template>
  <div class="w-100 h-100">
    <div class="trip-list">
      <Draggable class="row g-2" v-model="trips" item-key="contentId" v-if="0 < (trips.length ?? 0)">
        <template #item="{ element, index }">
          <div class="col-12" @click="focusTripMarker(parseInt(index))">
            <trip-search-card :trip="element" @title-click.stop="openTripReview(element)"></trip-search-card>
          </div>
        </template>
      </Draggable>
    </div>

    <l-map ref="map" v-model:zoom="zoomLevel" v-model:center="mapCenter">
      <l-tile-layer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" attribution="© OpenStreetMap contributors" layer-type="base" name="OpenStreetMap">
      </l-tile-layer>

      <l-marker ref="markers" v-for="(trip, i) in trips" :key="i" :lat-lng="[trip.latitude, trip.longitude]">
        <l-popup>
          <trip-popup :trip="trip" @click="openTripReview(trip)"></trip-popup>
        </l-popup>
      </l-marker>
    </l-map>
  </div>

  <Teleport to="#app">
    <hideable-edge-content v-model:show="showReview" width="600px" scrollable="true">
      <review-view :default="reviewTargetTrip">
      </review-view>
    </hideable-edge-content>
  </Teleport>
</template>

<style>
/* Leaflet 마커 팝업 margin 제거 */
.leaflet-popup-content {
  margin: 0 !important;
}

.trip-list {
  position: absolute;
  z-index: 5000;
  right: 0;

  padding: 12px;
  width: 400px;
  height: calc(100%);

  overflow-y: scroll;
}
</style>
