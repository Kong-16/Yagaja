<script setup lang="ts">
import NavigationBarTripSearchBox from '@/components/NavigationBarTripSearchBox.vue';
import { onBeforeMount, onBeforeUnmount, ref } from 'vue';
import { Authentication, probe, signout } from '@/apis/auth';
import { useRouter } from 'vue-router';

const router = useRouter();
const auth = ref<Authentication | null>(null);
const isSerachModeActivated = ref(false);

async function doSignout() {
  await signout();
  router.push('/');
}

function activateSearchMode() {
  isSerachModeActivated.value = true;
}

function deactivateSearchMode() {
  isSerachModeActivated.value = false;
}

function handleAuthChange(e: CustomEvent) {
  auth.value = e.detail as Authentication;
}

onBeforeMount(async () => {
  auth.value = await probe();
  self.addEventListener('activateSearchMode', activateSearchMode);
  self.addEventListener('deactivateSearchMode', deactivateSearchMode);
  self.addEventListener('authchange', handleAuthChange as any);  // eslint-disable-line
});

onBeforeUnmount(() => {
  self.removeEventListener('activateSearchMode', activateSearchMode);
  self.removeEventListener('authchange', handleAuthChange as any);  // eslint-disable-line
  self.removeEventListener('deactivateSearchMode', deactivateSearchMode);
});
</script>

<template>
  <nav class="nb" :class="{ 'search-mode': isSerachModeActivated }">
    <div class="container-xxl position-relative">
      <h1 class="position-absolute d-inline ms-2 my-0" style="left: 0; z-index: 10000" @click="$router.push('/')">
        <strong style="font-family: inherit">YA</strong>gaja
      </h1>

      <div class="profile-block dropdown me-2 position-absolute" style="right: 0">
        <div class="profile-block-container dropdown-toggle h-100 d-flex align-items-center" role="button"
             data-bs-toggle="dropdown" aria-expanded="false">
          <i class="bi bi-list" style="font-size: 1.5rem"></i>

          <img class="ms-1 h-75" src="@/assets/svg/no-profile.svg" alt="profile image" />

        </div>

        <!-- 화면 좌측 상단 드롭다운 목록 아이템 -->
        <ul class="dropdown-menu dropdown-menu-end mt-2">
          <li><a class="dropdown-item" @click="$router.push({ name: 'signup' })"
                 v-if="!auth?.authenticated"><strong>회원가입</strong></a></li>
          <li><a class="dropdown-item" @click="$router.push({ name: 'login' })" v-if="!auth?.authenticated">로그인</a></li>
          <li><a class="dropdown-item" @click="$router.push({ name: 'mypage', params: { userId: auth?.userInfo?.id } })"
                 v-if="auth?.authenticated">마이페이지</a></li>
          <li><a class="dropdown-item" @click="doSignout()" v-if="auth?.authenticated">로그아웃</a></li>
          <li>
            <hr class="dropdown-divider">
          </li>
          <li><a class="dropdown-item" @click="$router.push({ name: 'plans/edit' })">여행 계획</a></li>
        </ul>
      </div>

      <navigation-bar-trip-search-box @click="activateSearchMode"></navigation-bar-trip-search-box>
    </div>
  </nav>
</template>

<style scoped>
/***********************************************************************************************************************
   components.NavigationBar
  
   Abbreviations:
   - nb: navigation-bar
   **********************************************************************************************************************/

.nb {
  position: fixed;
  display: flex;
  justify-content: center;

  top: 0;
  z-index: 10000;

  border-bottom: var(--color-gray-0) 1px solid;
  padding: 0.9rem 0 0.9rem 0 !important;
  width: 100%;
  height: calc(var(--nb-height) + 1.8rem) !important;

  background: white;
}

.nb h1 {
  cursor: pointer;
  user-select: none;

  height: var(--nb-height);
  line-height: var(--nb-height);

  font-family: 'designhouseOTFLight00', 'Courier New', Courier, monospace !important;
}

/***********************************************************************************************************************
   components.NavigationBar
   > Profile & Menu
   **********************************************************************************************************************/

/* to hide Bootstrap5 Dropdown indicator */
.nb .profile-block-container::after {
  display: none;
}

.nb .profile-block {
  --profile-block-height: var(--nb-height);

  display: inline-block;
  cursor: pointer;
  z-index: 50000;

  transition: box-shadow 0.2s;

  border-radius: calc(var(--profile-block-height) / 2);
  border: var(--color-gray-1) 1px solid;
  padding: 0 0.7rem;
  height: var(--profile-block-height) !important;
}

.nb .profile-block:hover {
  box-shadow: var(--color-gray-0) 0 0 11px 1px;
}

.nb .profile-block .dropdown-menu {
  z-index: 50000;
  right: -0.7rem !important;
  border-radius: 1rem;
  box-shadow: var(--color-gray-0) 0 0 11px 1px;
  position: relative;
}

.nb .profile-block .dropdown-item {
  padding-top: 0.4rem !important;
  padding-bottom: 0.4rem !important;
}

/*******************************************************************************
   * components.EnjoyTripNav
   *   Search mode CSS
   ******************************************************************************/

.nb.search-mode {
  height: calc(var(--nb-search-mode-height) + 1.8rem) !important;
}

.nb .nb-tsb {
  top: 0;
}

.nb,
.nb .nb-tsb,
.nb .nb-tsb-container,
.nb .nb-tsb #btn-nb-tsb-search {
  transition: top 0.6s, border-radius 0.6s, width 0.6s, height 0.6s, box-shadow 0.2s !important;
}

.search-mode .nb-tsb {
  top: calc(var(--nb-height) * 2);
}
</style>
