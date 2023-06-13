<script setup lang="ts">
import { defineProps, ref, watchEffect, defineEmits } from 'vue';
import TextOption from './TextOption.vue';

defineProps(['modelValue']);
const emits = defineEmits<{
  (e: 'update:modelValue', value: unknown): void;
}>();

const ctypeId = ref(-1);
watchEffect(() => emits('update:modelValue', ctypeId.value));

const options: [string, number][] = [
  ['전체', -1],
  ['관광지', 12],
  ['숙박', 32],
  ['음식점', 39],
  ['문화시설', 14],
  ['공연', 15],
  ['여행코스', 25],
  ['쇼핑', 38],
];
</script>

<template>
  <div class=" content-type-id-list container-fluid d-flex justify-content-center">
    <ul class="list-group list-group-horizontal user-select-none d-flex justify-content-between w-100" style="max-width: 500px">
      <li v-for="(key, index) in options" :key="index" class="list-group-item border-0 p-1 flex-shrink-0">
        <text-option name="tour-type" :option-value="key[1]" :label="key[0]" v-model="ctypeId"></text-option>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.content-type-id-list {
  opacity: 0;
  transition: opacity 0.6s;
  visibility: hidden;
}

.search-mode .content-type-id-list {
  opacity: 1;
  position: relative;
  top: -20px;
  visibility: visible;
}
</style>
