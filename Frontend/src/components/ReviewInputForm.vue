<script setup lang="ts">
import { Authentication } from '@/apis/auth';
import { TripReviewDto } from '@/apis/reviews';
import { defineEmits, defineProps, onMounted, ref } from 'vue';

const emits = defineEmits<{
  (eventType: 'submit', reviewContent: string): void;
  (eventType: 'cancel', e: MouseEvent): void;
}>();

defineProps<{ auth: Authentication | null; review?: TripReviewDto | null }>();
const reviewContentEl = ref<HTMLTextAreaElement | null>(null);

function resizeReviewContentEl() {
  if (reviewContentEl.value !== null) {
    reviewContentEl.value.style.height = 'auto';
    reviewContentEl.value.style.height = `${reviewContentEl.value.scrollHeight}px`;
  }
}

function doSubmit() {
  if (reviewContentEl.value?.value) {
    const content = reviewContentEl.value.value;
    reviewContentEl.value.value = '';
    resizeReviewContentEl();
    emits('submit', content);
  }
}

onMounted(resizeReviewContentEl);
</script>

<template>
  <header class="rv-header">
    <!-- 유저 프로파일 -->
    <div class="rv-author-profile-container">
      <img class="rv-author-profile" src="@/assets/svg/no-profile.svg" alt="a profile image" />
    </div>

    <!-- 유저 정보 -->
    <div class="rv-author-info-container">
      <div class="rv-author-info">
        <h4 class="rv-author-name">{{ auth?.userInfo?.name ?? '익명의 사용자' }}</h4>
        <p class="rv-modified-date" v-if="!auth?.authenticated">로그인하여 리뷰를 작성해주세요 !</p>
        <p class="rv-modified-date" v-else>{{ review ? '수정중' : '여러분의 소중한 경험을 공유해주세요 !' }}</p>
      </div>
    </div>

    <!-- 버튼 목록 -->
    <div class="rv-btn-container">
      <button type="button" @click="doSubmit()">
        <img src="@/assets/svg/send.svg" alt="write review button icon" />
      </button>

      <button type="button" @click="emits('cancel', $event)" v-if="review">
        <img src="@/assets/svg/close.svg" alt="cacel editing button icon" />
      </button>
    </div>
  </header>

  <textarea
    ref="reviewContentEl"
    rows="1"
    placeholder="리뷰 내용을 입력해주세요."
    :value="review?.content"
    @input="resizeReviewContentEl()"
    v-if="auth?.authenticated"
  ></textarea>
</template>

<style scoped>
.rv-header {
  display: flex;
}

.rv-author-profile-container {
  border-radius: 22px;
  width: 44px;
  height: 44px;

  flex: 0 0 auto;
}

.rv-author-profile {
  width: 100%;
  height: 100%;
}

.rv-author-profile[src$='.svg'] {
  transform: scale(1.2);
}

.rv-author-info-container {
  min-width: 0;
  flex: 1 1 auto;
  width: 480px;
}

.rv-author-info {
  padding: 1px 12px;
}

.rv-author-name {
  font-size: 18px;
  width: 100%;
  margin: 0;
}

.rv-modified-date {
  margin: 0;
  color: var(--color-gray-4);
  font-size: 14px;
  transform: translateY(-2px);
}

textarea {
  resize: none !important;
  outline: none !important;
  margin-top: 8px;
  border: none;
  border-bottom: var(--color-gray-1) 1px solid !important;
  width: 100%;
  color: var(--color-gray-3);

  scrollbar-width: none;
  -ms-overflow-style: none;

  transition: border 0.2s, color 0.2s;
}

textarea:focus {
  color: black;
  border-bottom: var(--color-gray-3) 1px solid !important;
}

textarea::-webkit-scrollbar {
  display: none;
}

.rv-btn-container {
  display: flex;
  flex-shrink: 1;
  flex-direction: row-reverse;
  gap: 8px;

  margin-left: auto;
  width: 100%;
  min-width: 0;
}

.rv-btn-container button {
  display: flex;
  justify-content: center;
  align-items: center;

  outline: none;
  padding: 0;
  margin: 0;
  border: transparent 0px;
  border-radius: 16px;
  background: transparent;
  width: 28px;
  height: 28px;

  transition: background 0.3s, transform 0.5s, box-shadow 0.3s;
}

.rv-btn-container button img {
  width: 26px;
  height: 26px;
}
.rv-btn-container button:hover {
  background: var(--color-gray-0);
  box-shadow: var(--color-gray-0) 0 0 8px 1px;
  transform: rotate(8deg);
}
</style>
