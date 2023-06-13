<script setup lang="ts">
import EmbeddedTripReview from '@/components/EmbeddedTripReview.vue';
import ReviewInputForm from '@/components/ReviewInputForm.vue';
import { Authentication, probe } from '@/apis/auth';
import { LMap, LTileLayer, LMarker } from '@vue-leaflet/vue-leaflet';
import { TripInfo } from '../types';
import { defineProps, onBeforeMount, onBeforeUnmount, ref, watch, watchEffect } from 'vue';
import { fetchReviews, TripReviewDto } from '@/apis/reviews';
import { useRouter } from 'vue-router';
import { computed } from '@vue/reactivity';

const props = defineProps<{
  contentId?: number;
  default?: TripInfo;
}>();

const $router = useRouter();
const trip = ref<TripInfo | null>(props.default ?? null);
const zoomLevel = ref(16);
const auth = ref<Authentication | null>(null);
const tripLatLng = computed(() => [trip.value?.latitude, trip.value?.longitude]);
const tripReviews = ref<TripReviewDto[] | null>(null);

async function fetchAuth() {
  auth.value = await probe();
}

async function fetchTripInfo(contentId?: number | string) {
  if (!props.default && contentId !== undefined) {
    const resp = await fetch(`/attractions/${contentId}`);
    if (!resp.ok) {
      $router.back();
    }

    trip.value = (await resp.json()) as TripInfo;
  }
}

watch(
  () => props.contentId,
  () => fetchTripInfo(props.contentId)
);

watch(
  () => props.default,
  () => {
    if (props.default) {
      trip.value = props.default;
    }
  }
);

watchEffect(async () => {
  if (trip.value !== null) {
    tripReviews.value = await fetchReviews(trip.value.contentId);
  }
});

async function writeReview(content: string) {
  if (trip.value !== null) {
    await fetch(`/reviews`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json;charset=utf-8' },
      body: JSON.stringify({ content, attractionId: trip.value.contentId }),
    });

    tripReviews.value = await fetchReviews(trip.value.contentId);
  }
}

async function modifyReview(review: TripReviewDto, content: string) {
  if (trip.value !== null) {
    fetch(`/reviews/${review.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json;charset=utf-8',
      },
      body: JSON.stringify({
        content: content,
        attractionId: trip.value.contentId,
      }),
    });

    review.content = content;
  }
}

async function deleteReview(reviewId: number) {
  await fetch(`/reviews/${reviewId}`, { method: 'DELETE' });
  if (trip.value !== null) {
    tripReviews.value = await fetchReviews(trip.value.contentId);
  }
}

function handleAuthChange(e: Event) {
  auth.value = (e as CustomEvent).detail as Authentication;
}

onBeforeMount(async () => {
  await fetchAuth();
  self.addEventListener('authchange', handleAuthChange);
});

onBeforeUnmount(() => {
  self.removeEventListener('authchange', handleAuthChange);
});
</script>

<template>
  <div class="rv-trip-main-thumbnail-container">
    <div ref="tripMainThumbnailEl" class="rv-trip-main-thumbnail">
      <img :src="trip?.thumbnail || '/assets/svg/no-image.svg'" alt="trip thumbnail" />
    </div>
  </div>

  <div class="rv-main">
    <h1 class="rv-title">
      {{ trip?.title }}
    </h1>

    <!-- 전화번호 & 우편번호 -->
    <p class="my-0">
      <i class="bi bi-telephone-fill"></i>
      <span class="tsc-tel ms-2">{{ !trip?.tel.length ? '연락처 정보가 없어요.' : `Tel. ${trip.tel}` }}</span>
    </p>
    <p class="my-0">
      <i class="bi bi-mailbox"></i>
      <span class="tsc-zipcode ms-2">{{ !trip?.zipcode.length ? '우편 정보가 없어요.' : `우편 번호 ${trip.zipcode}` }}</span>
    </p>

    <br />

    <div class="rv-map-container">
      <l-map ref="map" v-model:zoom="zoomLevel" :center="tripLatLng">
        <l-tile-layer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution="© OpenStreetMap contributors"
          layer-type="base"
          name="OpenStreetMap"
        >
        </l-tile-layer>
        <l-marker :lat-lng="tripLatLng"></l-marker>
      </l-map>
    </div>

    <div class="rv-divider"></div>

    <review-input-form :auth="auth" @submit="writeReview"></review-input-form>

    <div class="rv-review-list-container">
      <section class="rv-review-container" v-for="(review, i) in tripReviews" :key="i">
        <embedded-trip-review
          :trip="trip"
          :review="review"
          :auth="auth"
          @modify="(content) => modifyReview(review, content)"
          @delete="deleteReview(review.id)"
        ></embedded-trip-review>
      </section>
    </div>
  </div>
</template>

<style scoped>
.rv-trip-main-thumbnail-container {
  display: flex;
  justify-content: center;
  align-items: center;

  height: 35vh;
  min-height: 240px;

  background: rgb(245, 245, 245);
  /* box-shadow: rgba(0, 0, 0, 0.2) 0 0 10px 4px; */
}

.rv-trip-main-thumbnail {
  display: flex;
  justify-content: center;
  align-items: center;

  width: 100%;
  height: 100%;
}

.rv-trip-main-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.rv-trip-main-thumbnail img[src$='.svg'] {
  width: 35vh;
  min-width: 240px;

  background-attachment: fixed;
  background-size: contain;
  transform: scale(0.5);
}

.rv-main {
  width: 100%;
  padding: 1.4rem;
  font-size: 1rem;
}

.rv-title {
  font-size: 1.4rem;
}

.rv-map-container {
  position: relative;
  border-radius: 12px;
  overflow: hidden;
  height: 240px;
}

.rv-review-list-container h2 {
  font-size: 24px;
  height: 24px;
}

.rv-review-list-container img {
  height: 100%;
}

.rv-review-container {
  margin-top: 24px;
}

.rv-divider {
  height: 1px;
  margin: 32px 0;
  margin-left: 40% !important;
  width: 20%;
  background: var(--color-gray-1);
  /* box-shadow: rgb(0, 0, 0, 0.1) 0 0 1px 0px; */
}

button {
  margin-top: 30px;
}
</style>
