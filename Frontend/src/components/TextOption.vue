<script setup lang="ts">
import { computed, defineEmits, defineProps } from 'vue';

defineEmits(['update:modelValue']);

const props = defineProps<{
  name: string;
  label: string;
  optionValue: unknown;
  modelValue: unknown;
}>();

const dummyId = computed(() => `${props.name}-${btoa(encodeURIComponent(props.label)).replace(/=/g, '')}`);
</script>

<template>
  <div class="text-option">
    <label :for="dummyId">{{ label }}</label>
    <input
      ref="radioBtnEl"
      type="radio"
      :name="name"
      :id="dummyId"
      :value="optionValue"
      :checked="modelValue == optionValue"
      @input="$emit('update:modelValue', optionValue)"
    />

    <div class="text-option-underbar-container">
      <div class="text-option-underbar"></div>
    </div>
  </div>
</template>

<style scoped>
.text-option {
  display: inline-block;
}

.text-option input {
  display: none;
}

.text-option label {
  cursor: pointer;

  display: flex;
  align-items: center;

  width: 100%;
  height: 100%;

  font-size: 1rem;

  transition: font-size 0.15s;
}

.text-option input:not(:checked):hover ~ label {
  color: gray;
}

.text-option input:checked ~ label {
  font-size: 1rem;
}

.text-option .text-option-underbar-container {
  display: flex;
  justify-content: center;
  width: 100%;
  height: 2px;
}

.text-option .text-option-underbar {
  width: 0px;
  height: 100%;
  background: gray;
  transition: width 0.15s;
}

.text-option input:not(:checked):hover ~ * > .text-option-underbar {
  width: 100%;
}

.text-option input:checked ~ * > .text-option-underbar {
  width: 100% !important;
  background: black;
}
</style>
