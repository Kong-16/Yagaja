<script setup lang="ts">
import { defineProps, defineEmits, watchEffect, ref } from 'vue';

const props = defineProps<{
  show: boolean;
  width?: number | string;
  scrollable?: string;
}>();
const emits = defineEmits<{
  (type: 'update:show', newValue: boolean): void;
}>();

const containerEl = ref<HTMLElement | null>(null);
const containerElStyle = ref<any>({});  // eslint-disable-line

function onTransitionEnter(el: HTMLElement) {
  const bb = el.getBoundingClientRect();
  el.style.left = `-${bb.width}px`;
  el.style.opacity = '0';
  el.classList.remove('slide-in');
}

function onTransitionAfterEnter(el: HTMLElement) {
  el.classList.add('slide-in');
}

watchEffect(() => {
  if (containerEl.value !== null && props.width !== undefined) {
    if (typeof props.width === 'number') {
      containerEl.value.style.width = `${props.width}px`;
    } else {
      containerEl.value.style.width = props.width;
    }
  }
});
watchEffect(() => {
  containerElStyle.value['overflowY'] = props.scrollable === 'true' ? 'scroll' : 'hidden';
});
</script>

<template>
  <Transition name="slide-in-out" @enter="onTransitionEnter" @after-enter="onTransitionAfterEnter"
    @leave="onTransitionEnter">
    <div ref="containerEl" class="hideable-edge-content-container" v-if="show">
      <div class="hideable-edge-content" :style="containerElStyle">
        <button type="button" @click="emits('update:show', false)">
          <img src="@/assets/svg/close.svg" alt="close button" />
        </button>
        <slot></slot>
      </div>
    </div>
  </Transition>
</template>

<style scoped>
.hideable-edge-content-container {
  position: fixed;
  z-index: 5000;
  overflow: hidden;

  top: var(--nb-bounding-rect-height);
  left: 0;

  box-shadow: var(--color-gray-2) 0 0 11px 1px;
  border-top-right-radius: 12px;
  border-bottom-right-radius: 12px;
  height: calc(100% - var(--nb-bounding-rect-height));

  background: white;
}

.hideable-edge-content-container.slide-in {
  left: 0 !important;
  opacity: 1 !important;
  transition: left 0.6s cubic-bezier(0.215, 0.61, 0.355, 1), opacity 0.3s cubic-bezier(0.215, 0.61, 0.355, 1);
}

.slide-in-out-leave-active {
  opacity: 1 !important;
  transition: left 0.6s cubic-bezier(0.215, 0.61, 0.355, 1);
}

.hideable-edge-content {
  position: relative;
  width: 100%;
  height: 100%;

  scrollbar-width: none;
  -ms-overflow-style: none;
}
.hideable-edge-content::-webkit-scrollbar {
  display: none;
}

button {
  outline: none;
  border: none;

  position: absolute;
  right: 0;

  padding: 0px;
  width: 32px;
  height: 32px;

  background: transparent;
}

button img {
  width: 100%;
  height: 100%;
}
</style>
