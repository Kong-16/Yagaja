<script setup lang="ts">
import Draggable from 'zhyswan-vuedraggable';
import { TripDto } from '../apis/trips';
import { defineProps, defineEmits } from 'vue';

const emits = defineEmits<{ (eventType: 'click', e: MouseEvent): void }>();
defineProps<{
  trip: TripDto;
  draggableGroup?: { name: string, pull: string, put: boolean };
  draggableClone?: (trip: TripDto) => any;  // eslint-disable-line
}>();
</script>

<template>
  <Draggable class="dragArea" :list="[trip]" item-key="contentId" :group="draggableGroup" :clone="draggableClone">
    <template #item>
      <section class="tp" @click="emits('click', $event)">
        <div class="d-flex">
          <div class="tp-thumbnail">
            <img :src="trip.thumbnail || '/assets/svg/no-image.svg'" alt="trip popup thumbnail">
          </div>
          <div class="tp-body">
            <h5 class="tp-title">{{ trip.title }}</h5>
            <span class="tp-address">{{ trip.address }}</span>
            <br />
            <br />
            <i class="bi bi-telephone-fill ms-1"></i>
            <span class="ms-1">{{ trip.tel || '연락처 정보가 없어요.' }}</span>
            <br />
            <i class="bi bi-mailbox ms-1"></i>
            <span class="ms-1">{{ trip.zipcode || '우편 정보가 없어요.' }}</span>
          </div>
        </div>
      </section>
    </template>
  </Draggable>
</template>

<style scoped>
.tp {
  --tp-height: 120px;

  cursor: grab;

  overflow: hidden;
  border-radius: 12px;
  width: 300px;
  height: var(--tp-height);
}

.tp-thumbnail {
  flex-shrink: 0;
  width: var(--tp-height);
  height: var(--tp-height);
  background: rgb(245, 245, 245);
}

.tp-thumbnail img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transform: scale(1.03);
  border-radius: 4px;
}

.tp-thumbnail img[src$=".svg"] {
  transform: scale(0.5);
}

.tp-body {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  border-top-right-radius: 12px;
  border-bottom-right-radius: 12px;

  flex-grow: 1;
  padding: 12px;
  font-size: 12px !important;

  background: white;
}

.tp-title {
  cursor: pointer;

  margin: 0;
  font-size: 1rem;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.tp-address {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
</style>
