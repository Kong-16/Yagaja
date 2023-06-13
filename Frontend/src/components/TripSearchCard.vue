<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';
import { TripDto } from '@/apis/trips';

const props = defineProps<{ trip: TripDto }>();
const emits = defineEmits<{
  (eventType: 'titleClick', e: Event, tripInfo: TripDto): void;
}>();
</script>

<template>
  <article class="tsc">
    <div class="tsc-thumbnail">
      <img :src="props.trip.thumbnail || '/assets/svg/no-image.svg'" class="tsc-thumbnail" alt="" />
    </div>
    <div class="tsc-content">
      <h2 @click="emits('titleClick', $event, props.trip)">{{ trip.title }}</h2>

      <p class="tsc-addr">{{ trip.address }}</p>

      <!-- 전화번호 & 우편번호 -->
      <p class="my-0">
        <i class="bi bi-telephone-fill"></i>
        <span class="tsc-tel ms-2">{{ trip.tel ? trip.tel : '연락처 정보가 없어요.' }}</span>
      </p>
      <p class="my-0">
        <i class="bi bi-mailbox"></i>
        <span class="tsc-zipcode ms-2">{{ trip.zipcode ? trip.zipcode : '우편 정보가 없어요.' }}</span>
      </p>
    </div>
  </article>
</template>

<style scoped>
.tsc {
  --tsc-height: 128px;
  --tsc-width: calc(var(--tsc-height) * 2.5);

  cursor: pointer;
  display: flex;
  overflow: hidden;

  position: relative;
  border-radius: 12px;
  width: 100%;
  height: var(--tsc-height);

  background: white;
  transition: box-shadow 0.2s, transform 0.2s;
}

.tsc:hover {
  box-shadow: rgba(96, 96, 96, 0.5) 0px 0px 6px 3px;
  transform: scale(1.01);
}

.tsc-thumbnail {
  flex: 0 0 auto;
  border-radius: 12px;
  width: var(--tsc-height);
  height: var(--tsc-height);

  background: rgb(245, 245, 245);

  transition: width 0.2s;
}

.tsc:hover .tsc-thumbnail {
  width: 0px;
}

.tsc-thumbnail img {
  height: 100%;
  object-fit: cover;
}

.tsc-thumbnail img[src$='.svg'] {
  transform: scale(0.5);
}

.tsc-content {
  padding: 12px;
  min-width: 0;
  font-size: 12px;
}

.tsc-content h2 {
  font-size: 1.2rem;
  transition: all 0.3s;
}

.tsc-content h2:hover {
  text-shadow: rgba(192, 192, 192, 0.650) 0px 0px 6px;
  transform: translate(0, -1px);
}

.tsc-content h2,
.tsc-content p,
.tsc-content span {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.tsc-addr {
  font-size: 0.8rem;
}
</style>
