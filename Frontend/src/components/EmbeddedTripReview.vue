<script setup lang="ts">
import ReviewInputForm from '@/components/ReviewInputForm.vue';
import { Authentication } from '@/apis/auth';
import { TripReviewDto } from '@/apis/reviews';
import { computed } from '@vue/reactivity';
import { defineProps, defineEmits, ref } from 'vue';

const emits = defineEmits<{
  (eventType: 'modify', content: string): void;
  (eventType: 'delete', e: MouseEvent): void;
}>();

const props = defineProps<{ review: TripReviewDto; auth: Authentication | null }>();
const inEditing = ref<boolean>(false);
const yy = computed(() => new Date(props.review.modifiedDate).getFullYear());
const mm = computed(() => new Date(props.review.modifiedDate).getMonth() + 1);
const dd = computed(() => new Date(props.review.modifiedDate).getDate());
</script>

<template>
  <header class="rv-header" v-if="!inEditing">
    <div class="rv-author-profile-container">
      <img class="rv-author-profile" src="@/assets/svg/no-profile.svg" alt="a profile image" />
    </div>
    <div class="rv-author-info-container">
      <div class="rv-author-info">
        <h4 class="rv-author-name">{{ review.userId }}</h4>
        <p class="rv-modified-date">{{ `${yy}.${mm}.${dd} 작성` }}</p>
      </div>
    </div>

    <!-- 버튼 목록 -->
    <div class="rv-btn-container" v-if="auth?.userInfo?.id === review.userId">
      <button type="button" @click="emits('delete', $event)">
        <img src="@/assets/svg/delete.svg" alt="write review button icon" />
      </button>
      <button type="button" @click="inEditing = true">
        <img src="@/assets/svg/pencil.svg" alt="write review button icon" />
      </button>
    </div>
  </header>

  <main style="margin-top: 8px" v-if="!inEditing">
    <p v-for="(line, i) in review.content.split('\n')" :key=i>{{ line }}</p>
  </main>

  <review-input-form
    v-if="inEditing"
    :auth="auth"
    :review="review"
    @cancel="inEditing = false"
    @submit="
      (content) => {
        inEditing = false;
        emits('modify', content);
      }
    "
  ></review-input-form>
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
  width: 400px;
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

main p {
  margin-bottom: 4px;
}
</style>
