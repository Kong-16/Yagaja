<script setup lang="ts">
import {defineProps} from 'vue';
import {TripInfo} from '../types';

const props = defineProps<{
  tripInfo: TripInfo;
}>();

</script>

<template>
  <article class="tsc">
    <div class="tsc-thumbnail-container">
      <img :src="props.tripInfo.thumbnail" class="tsc-thumbnail"  alt="">
      <img src="@/assets/svg/no-image.svg" alt="no thumbnail">
    </div>

    <h2 class="tsc-title ms-3 mt-3 mb-1">{{ tripInfo.title || '이름 정보가 없어요' }}</h2>

    <p class="tsc-addr ms-3">{{ tripInfo.address }}</p>

    <!-- 전화번호 -->
    <p class="my-0">
      <i class="bi bi-telephone-fill ms-3"></i>
      <span class="tsc-tel ms-3">{{ tripInfo.tel ? tripInfo.tel : '연락처 정보가 없어요.' }}</span>
    </p>

    <p class="my-0">
      <i class="bi bi-mailbox ms-3"></i>
      <span class="tsc-zipcode ms-3">{{ tripInfo.zipcode ? tripInfo.zipcode : '우편 정보가 없어요.' }}</span>
    </p>

    <button type="button" class="btn-view-details">자세한 정보 보러가기</button>
  </article>
</template>

<style scoped>
.tsc {
  cursor: pointer;

  position: relative;

  border-radius: 0.8rem;
  padding-bottom: 0.6rem;

  transition: box-shadow 0.2s;
}

.tsc:hover {
  box-shadow: var(--color-gray-0) 0 0 11px 1px;
}

.tsc-thumbnail-container {
  height: 16rem;
  border-radius: 0.8rem;
  overflow: hidden;
  position: relative;
}

.tsc-thumbnail {
  object-fit: cover;
  width: 100%;
  height: 100%;
  position: absolute;
  z-index: 1;
}

.tsc-thumbnail-container img[src$=".svg"] {
    object-fit: scale-down;
    width: 100%;
    height: 100%;
    transform: scale(0.4);
}

.tsc-title {
  font-size: 1rem;

  overflow-x: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.tsc-addr,
.tsc-tel,
.tsc-zipcode {
  font-size: 0.8rem;
  overflow-x: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.tsc i {
  font-size: 0.9rem;
}

.tsc button {
  display: block;

  border: none;
  margin-top: 8px;
  padding: 0;

  background: transparent;
  font-size: 0.8rem !important;
}
</style>
