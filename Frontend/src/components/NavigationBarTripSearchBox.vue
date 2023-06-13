<script setup lang="ts">
import ContentTypeIdList from './ContentTypeIdList.vue';
import { computed } from '@vue/reactivity';
import { defineEmits, ref, watchEffect } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const emits = defineEmits<{ (e: 'click', value: Event): void }>();

const areas = [
  ['전국', -1],
  ['서울', 1],
  ['인천', 2],
  ['대전', 3],
  ['대구', 4],
  ['광주', 5],
  ['부산', 6],
  ['울산', 7],
  ['세종', 8],
  ['경기도', 31],
  ['강원도', 32],
  ['충청북도', 33],
  ['충청남도', 34],
  ['경상북도', 35],
  ['경상남도', 36],
  ['전라북도', 37],
  ['전라남도', 38],
  ['제주도', 39],
];

const regions = ref([['전체', -1]]);
const keyword = ref('');
const selectedContentTypeId = ref(-1);
const selectedAreaIndex = ref(0);
const selectedRegionIndex = ref(0);
const selectedAreaName = computed(() => areas[selectedAreaIndex.value][0]);
const selectedRegionName = computed(() => regions.value[selectedRegionIndex.value][0]);

function search() {
  const isEmpty = (v: unknown) => (typeof v === 'string' ? v.length === 0 : v === -1);
  const query = Object.fromEntries(
    [
      ['keyword', keyword.value.trim()],
      ['contentTypeId', selectedContentTypeId.value],
      ['areaCode', areas[selectedAreaIndex.value][1]],
      ['regionCode', regions.value[selectedRegionIndex.value][1]],
    ].filter(([, v]) => !isEmpty(v))
  );

  self.dispatchEvent(new CustomEvent('deactivateSearchMode'));
  if (router.currentRoute.value.name !== 'plans/edit') {
    router.push({ name: 'search', query });
  } else {
    self.dispatchEvent(new CustomEvent('searchsubmit', { detail: query }));
  }
}

function handleSearchButtonClick(e: MouseEvent) {
  if ((e.target as Element).matches('.search-mode *')) {
    e.stopPropagation();
    search();
  }
}

function deactivateSearchMode() {
  self.dispatchEvent(new Event('deactivateSearchMode'));
}

watchEffect(async () => {
  regions.value = [['전체', -1]];
  selectedRegionIndex.value = 0;

  if (selectedAreaIndex.value !== 0) {
    const resp = await fetch(`/areas/${areas[selectedAreaIndex.value][1]}`);
    if (!resp.ok) {
      return;
    }

    const json = await resp.json() as [];
    regions.value = regions.value.concat(json.map(({ region_name, region_code }) => [region_name, region_code]));
  }
});
</script>

<template>
  <div class="nb-tsb">
    <div class="nb-tsb-container" @click="emits('click', $event)">
      <!-- 검색 버튼 -->
      <button type="button" id="btn-nb-tsb-search" @click="handleSearchButtonClick">
        <img src="@/assets/svg/search.svg" style="width: 65%; height: 65%" alt="trip search button" />
      </button>

      <!-- 광역시/도 목록 -->
      <div class="nb-tsb-item dropdown">
        <div class="nb-tsb-item-content dropdown-toggle" id="dropdown-areas" data-bs-toggle="dropdown"
          aria-expanded="false">
          <div class="nb-tsb-item-header">광역시·도</div>
          <div class="nb-tsb-item-body">{{ selectedAreaName }}</div>
        </div>

        <ul class="dropdown-menu mt-2" aria-labelledby="dropdown-areas">
          <li class="dropdown-item d-flex align-items-center" v-for="(key, index) in areas" :key="index"
            @click="selectedAreaIndex = index">
            <span>{{ key[0] }}</span>
          </li>
        </ul>
      </div>

      <div class="nb-tsb-spliter"></div>

      <!-- 시/군/자치구 목록 -->
      <div class="nb-tsb-item dropdown" id="sigungu-list">
        <div class="nb-tsb-item-content dropdown-toggle" id="dropdown-signugu" data-bs-toggle="dropdown"
          aria-expanded="false">
          <div class="nb-tsb-item-header">시·군·자치구</div>
          <div class="nb-tsb-item-body">{{ selectedRegionName }}</div>
        </div>

        <ul class="dropdown-menu mt-2" aria-labelledby="dropdown-sigungu">
          <li class="dropdown-item d-flex align-items-center" v-for="(region, index) in regions" :key="index"
            @click="selectedRegionIndex = index">
            <span>{{ region[0] }}</span>
          </li>
        </ul>
      </div>

      <div class="nb-tsb-spliter"></div>

      <div class="nb-tsb-item">
        <div class="nb-tsb-item-content">
          <div class="nb-tsb-item-header">검색어</div>
          <div><input type="text" v-model="keyword" @keydown.esc="deactivateSearchMode()" @keydown.enter="search" />
          </div>
        </div>
      </div>

      <div class="placeholder ps-3">
        <span class="d-block d-sm-none">준비됐나요?</span>
        <span class="d-none d-sm-block d-md-none">여행을 떠나볼까요?</span>
        <span class="d-none d-md-block">여행을 떠날 준비는 되었나요?</span>
      </div>
    </div>
  </div>

  <content-type-id-list v-model="selectedContentTypeId"></content-type-id-list>
</template>

<style>
/***********************************************************************************************************************
 components.NavigationBar
 > components.NavigationBarTripSearchBox

 Abbreviations:
 - nb: navigation-bar
 - tsb: trip-search-box
 **********************************************************************************************************************/

.nb-tsb {
  position: relative;

  display: flex;
  justify-content: center;
  align-items: center;

  top: 0;
  margin: 0;
  height: var(--nb-tsb-height);
}

.search-mode .nb-tsb {
  top: calc(var(--nb-height) * 2);
  height: var(--nb-tsb-search-mode-height);
}

.nb-tsb-container {
  display: flex;
  align-items: center;

  position: relative;
  z-index: 10000;

  border: var(--color-gray-1) 1px solid;
  border-radius: calc(var(--nb-tsb-height) / 2);

  max-width: 800px;
  width: 35%;
  height: 100%;
}

.search-mode .nb-tsb-container {
  border-radius: calc(var(--nb-tsb-search-mode-height) / 2);
  width: 100%;
}

.nb-tsb-container:hover {
  cursor: pointer !important;
  box-shadow: var(--color-gray-0) 0 0 11px 1px;
}

/***********************************************************************************************************************
 components.NavigationBar
 > 빈 검색 상자 텍스트 플레이스 홀더
 **********************************************************************************************************************/

.nb-tsb .placeholder {
  display: block;
  background: transparent;
  color: var(--color-gray-4) !important;
  cursor: pointer !important;
}

.search-mode .nb-tsb .placeholder {
  display: none;
}

/***********************************************************************************************************************
 components.NavigationBar
 > 검색 상자 검색 버튼
 **********************************************************************************************************************/

.nb-tsb #btn-nb-tsb-search {
  position: absolute;
  z-index: 10000;

  right: calc(var(--nb-tsb-height) * (1 / 8));

  border: 0;
  border-radius: calc(var(--nb-tsb-height) * (3 / 8));
  margin: 0;
  padding: 0;

  width: calc(var(--nb-tsb-height) * (3 / 4));
  height: calc(var(--nb-tsb-height) * (3 / 4));
}

.search-mode .nb-tsb #btn-nb-tsb-search {
  right: calc(var(--nb-tsb-search-mode-height) * (1 / 8));

  border-radius: calc(var(--nb-tsb-search-mode-height) * (3 / 8));

  width: calc(var(--nb-tsb-search-mode-height) * (3 / 4));
  height: calc(var(--nb-tsb-search-mode-height) * (3 / 4));
}

/***********************************************************************************************************************
 components.NavigationBar
 > 검색 모드 아이템
 **********************************************************************************************************************/

.nb-tsb-item {
  position: relative;
  display: none;

  margin: 0;
  border-radius: calc(var(--nb-tsb-search-mode-height) / 2);
  padding: 0;

  min-width: 120px;
  height: 100%;

  cursor: pointer;
}

.search-mode .nb-tsb-item,
.search-mode .nb-tsb-spliter {
  display: block;
}

div.nb-tsb-item:nth-last-child(2) {
  flex-grow: 1;
}

.nb-tsb-item-content {
  border-radius: calc(var(--nb-tsb-search-mode-height) / 2);
  margin: 0;
  padding: 8px 28px;
  width: 100%;
  height: 100%;
}

.nb-tsb-item-content:hover {
  background: var(--color-gray-0) !important;
}

.nb-tsb-item-header {
  color: #606060;
  font-size: 0.82rem;
  transform: translateY(4px);
}

.nb-tsb-item-body {
  font-size: 1.1rem;
}

.nb-tsb-spliter {
  display: none;

  width: 1px;
  height: 60%;
  background: transparent;
  flex-shrink: 0;
}

.search-mode .nb-tsb-spliter {
  display: block;
}

.nb-tsb-item:not(:hover)+.nb-tsb-spliter {
  background: #bababa;
}

.nb-tsb-item:not(:first-of-type):hover::before {
  width: 1px;
  height: calc(var(--nb-tsb-search-mode-height) - 10px);
  background: white;
  position: absolute;
  left: -1px;
  top: 5px;
  content: '';
  z-index: 10001;
}

.nb-tsb-item:hover+.nb-tsb-item-spliter {
  background: transparent;
}

.nb-tsb input[type='text'] {
  padding: 0;
  background: transparent;
  border: none !important;
  outline: none !important;
  text-overflow: ellipsis;
  width: calc(100% - var(--nb-tsb-search-mode-height) / 2);
}

.nb-tsb .dropdown-menu {
  border-radius: 1rem;
  padding-top: 12px;
  padding-bottom: 12px;
}

.nb-tsb .dropdown-item {
  height: 2.5rem !important;
}

/* Bootstrap5 Dropdown 컴포넌트 인디케이터 제거 */
.nb-tsb-item .nb-tsb-item-content::after {
  content: none !important;
}

.nb-tsb,
.nb-tsb-container,
.nb-tsb #btn-nb-tsb-search {
  transition: top 0.6s, border-radius 0.6s, width 0.6s, height 0.6s, box-shadow 0.2s !important;
}
</style>
