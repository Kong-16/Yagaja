<script setup lang="ts">
import { defineProps, defineEmits } from 'vue';
import { TripDto } from '../apis/trips';

defineProps<{ trip: TripDto }>();
const emits = defineEmits<{
  (eventType: 'click', e: MouseEvent): void;
  (eventType: 'delete', e: MouseEvent): void;
}>();
</script>

<template>
  <div class="pew" @click="emits('click', $event)">
    <button class="pew-delete-btn" @click="emits('delete', $event)">
      <img src="@/assets/svg/delete.svg" alt="delete button icon">
    </button>
    <div class="pew-thumbnail">
      <img :src="trip.thumbnail || '/assets/svg/no-image.svg'" alt="trip thumbnail">
    </div>
    <div class="pew-content">
      <div class="pew-title"> {{ trip.title }} </div>
      <div class="pew-address"> {{ trip.address }} </div>
    </div>
  </div>
</template>

<style scoped>
/* pew is abbreviation for PlanEditorWaypoint. */

.pew {
  --pew-height: 56px;

  cursor: grab;
  display: flex;
  position: relative;

  border-radius: calc(var(--pew-height) / 4);
  margin: 12px 0;
  height: var(--pew-height);

  transition: box-shadow 0.2s;
}

.pew:hover {
  box-shadow: rgba(96, 96, 96, 0.2) 0px 0px 6px 3px;
}

.pew-delete-btn {
  position: absolute;
  top: 4px;
  right: 6px;

  margin: 0;
  padding: 0;
  width: 22px;
  height: 22px;

  outline: none;
  border: transparent 0;
  border-radius: 12px;
  background: transparent;

  transition: background 0.2s;
}

.pew-delete-btn:hover {
  background: var(--color-gray-0);
}

.pew-delete-btn img {
  width: 100%;
  height: 100%;

  transform: translateY(-1px) scale(0.8);
}

.pew-thumbnail {
  overflow: hidden;
  flex-shrink: 0;

  border-radius: calc(var(--pew-height) / 4);
  width: var(--pew-height);
  height: 100%;

  background: rgb(245, 245, 245);
}

.pew-thumbnail img {
  width: var(--pew-height);
  height: var(--pew-height);

  object-fit: cover;
}

.pew-thumbnail img[src$=".svg"] {
  transform: scale(0.5);
}

.pew-content {
  padding: 2px 12px;
  min-width: 0;
}

.pew-title {
  font-weight: 500;
  font-size: 16px;

  max-width: 196px;
}

.pew-address {
  padding-top: 4px;
  font-size: 12px;
}

.pew-title,
.pew-address {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
</style>