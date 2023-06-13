<script setup lang="ts">

import {useRoute} from "vue-router";
import {ref, watchEffect} from "vue";
import {Authentication, Plan, TripInfo} from "@/types";
import TripSearchCard from "@/components/TripSearchCard.vue";

const $route = useRoute();
const map = ref<any>(null);  // eslint-disable-line
const markers = ref<any>(null);  // eslint-disable-line
const trips = ref<TripInfo[]>([]);
const plans = ref<Plan[]>([]);
const auth = ref<Authentication | null>(null);

const tripToViewReview = ref<TripInfo>();
const showReview = ref(false);


async function getUserInfo() {
    const resp = await fetch(`/auth/probe`, {method :'POST'});
    const json = await resp.json() as Authentication;

    if (json.authenticated){
        auth.value = json;
    }

}

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
    if(auth.value?.authenticated) {
        const resp = await fetch('/plans');
        if (resp.ok) {
            const json = await resp.json() as Plan[];
            plans.value = json;
        }
    }
});
</script>

<template>
  <div class="trip-info-list">
    <div class="row g-2">
      <div class="col-12" v-for="(tripInfo, i) in trips" :key="i" @click="() => focusTripMarker(i)">
        <trip-search-card :trip-info="tripInfo" @title-click.stop="openTripReview"></trip-search-card>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>