<script setup lang="ts">
import Draggable from 'zhyswan-vuedraggable';
import PlanEditorWaypoint from './PlanEditorWaypoint.vue';
import { PlanDto } from '../apis/plans';
import { TripDto } from '../apis/trips';
import { deepCopy } from '../utils';
import { ref, defineProps, defineEmits, watch, onBeforeMount } from 'vue';

const emits = defineEmits<{
  (eventType: 'waypointClick', e: MouseEvent, trip: TripDto): void;
  (eventType: 'save', plan: PlanDto): void;
  (eventType: 'update:plan', plan: PlanDto): void;
}>();

const props = defineProps<{ plan?: PlanDto | null }>();
const plan = ref<PlanDto | null>(null);

onBeforeMount(
  () => (plan.value = deepCopy(props.plan) ?? { name: '나만의 여행 계획', subplans: [{ name: '하위 여행 계획 1', waypoints: [] }] })
);
watch(
  // plan 데이터 변경을 부모가 반여할 수 있도록 감시
  () => {
    const allSubplans = [...(plan.value?.subplans ?? [])];
    const allWaypoints = [...allSubplans.map(({ waypoints }) => waypoints).flat()];
    return { ...plan.value, ...allSubplans, ...allWaypoints };
  },
  () => {
    if (plan.value !== null) {
      emits('update:plan', plan.value);
    }
  }
);

function activateSearchMode() {
  self.dispatchEvent(new CustomEvent('activateSearchMode'));
}

function handleSubplanAdd() {
  plan.value?.subplans.push({ name: `하위 여행 계획 ${plan.value.subplans.length + 1}`, waypoints: [] });
}

function handleSubplanDelete(subplanIndex: number) {
  plan.value?.subplans.splice(subplanIndex, 1);
}

function handleWaypointDelete(subplanIndex: number, waypointIndex: number) {
  plan.value?.subplans[subplanIndex].waypoints.splice(waypointIndex, 1);
}

function handlePlanSave() {
  if (plan.value !== null) {
  emits('save', plan.value);
  }
}

</script>

<template>
  <div style="height: 100%; display: flex; flex-direction: column">
    <div class="pe-header">
      <div class="pe-plan-title">
        <input type="text" v-model="plan.name" />
      </div>
      <div class="pe-header-btn-container">
        <!-- 플랜 저장 버튼 -->
        <button class="btn" type="button" @click="handlePlanSave">
          <img src="@/assets/svg/save.svg" alt="save plan button icon" />
        </button>

        <!-- 서브 플랜 추가 버튼 -->
        <button class="btn" type="button" @click.stop="handleSubplanAdd">
          <img src="@/assets/svg/plus.svg" alt="add subplan button icon" />
        </button>
      </div>
    </div>

    <div class="pe-divider"></div>

    <div class="pe-subplan-list">
      <div v-for="(subplan, subplanIndex) in plan?.subplans" :key="subplanIndex" style="padding: 6px">
        <div class="pe-subplan-header">
          <input type="text" v-model="subplan.name" />
          <button class="btn" type="button" @click.stop="handleSubplanDelete(subplanIndex)">
            <img src="@/assets/svg/close.svg" alt="delete subplan icon" />
          </button>
        </div>

        <Draggable :list="subplan.waypoints" group="trips" item-key="index">
          <template #header>
            <div class="pe-subplan-placeholder" v-if="subplan.waypoints.length === 0" @click="activateSearchMode()">
              <img src="@/assets/svg/drag-drop.svg" alt="drag drop icon" />
              드래그 앤 드롭으로 여정 추가
            </div>
          </template>

          <template #item="{ element, index }">
            <plan-editor-waypoint
              :trip="element.trip"
              @delete.stop="handleWaypointDelete(subplanIndex, index)"
              @click="emits('waypointClick', $event, element.trip)"
            ></plan-editor-waypoint>
          </template>
        </Draggable>

        <div class="pe-subplan-list-divider" v-if="subplanIndex < (plan?.subplans?.length ?? 0) - 1"></div>
      </div>
    </div>
  </div>
</template>

<style scoped>
button img {
  width: 100%;
  height: 100%;
}

.pe-plan-title input[type='text'] {
  outline: none;
  border: none;
  width: 80%;
  height: 22px;
  font-size: 24px;
}

.pe-header {
  position: relative;
  padding: 8px 0 0;
}

.pe-header-btn-container {
  display: flex;
  flex-direction: row-reverse;
  align-items: center;
  gap: 8px;

  position: absolute;
  top: 8px;
  right: 0;

  margin: 0;
  padding: 0;
  height: 26px;
}

.pe-header-btn-container button,
.pe-subplan-header button {
  display: flex;
  justify-content: center;
  align-items: center;

  padding: 0;
  margin: 0;
  outline: none;
  border: transparent 0px;
  border-radius: 13px;
  width: 26px;
  height: 100%;

  transform: translateY(-2px);
}

.pe-header-btn-container button:hover,
.pe-subplan-header button:hover {
  background-color: var(--color-gray-0);
}

.pe-subplan-header button {
  position: absolute;
  right: 4px;
  top: 0;
}

.pe-header-btn-container button img {
  width: 18px;
  height: 18px;
}

.pe-subplan-list {
  overflow-y: scroll;
  scrollbar-width: none;
  -ms-overflow-style: none;

  height: 100%;
}

.pe-subplan-list::-webkit-scrollbar {
  display: none;
}

.pe-subplan-placeholder {
  cursor: pointer;

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  margin-bottom: 16px;
  height: 64px;
  font-size: 14px;
}

.pe-subplan-placeholder img {
  border-radius: 12px;
  margin-bottom: 8px;
  width: 48px;
  height: 48px;

  transition: background 0.1s;
}

.pe-subplan-placeholder:hover img {
  background: var(--color-gray-0);
}

.pe-subplan-header {
  position: relative;
  margin-bottom: 16px;
}

.pe-subplan-header input[type='text'] {
  outline: none;
  border: none;
  padding: 0;
  margin: 0;
  width: 90%;
}

.pe-divider {
  margin: 8px 0;
  width: 100%;
  height: 1px;
  background: var(--color-gray-1);
}

.pe-subplan-list-divider {
  margin: 24px 35% 8px;
  width: 30%;
  height: 1px;
  background: var(--color-gray-1);
}
</style>
