<script setup lang="ts">
import NavigationBar from './components/NavigationBar.vue';

function deactivateSearchMode() {
  self.dispatchEvent(new Event('deactivateSearchMode'));
}
</script>

<template>
  <navigation-bar></navigation-bar>

  <main id="content">
    <div class="search-mode-background" @click="deactivateSearchMode"></div>
    <router-view/>
  </main>
</template>

<style>
@import url('https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.6/dist/web/static/pretendard.css');

@font-face {
  font-family: 'designhouseOTFLight00';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_three@1.0/designhouseOTFLight00.woff') format('woff');
  font-weight: normal;
  font-style: normal;
}

:root {
  --color-gray-0: #d4d4d4;
  --color-gray-1: #b4b4b4;
  --color-gray-2: #909090;
  --color-gray-3: #636363;
  --color-gray-4: #494848;

  --nb-height: 48px;
  --nb-bounding-rect-height: calc(var(--nb-height) + 1.8rem);
  --nb-search-mode-height: calc(var(--nb-height) * 3.5);

  --nb-tsb-height: var(--nb-height);
  --nb-tsb-search-mode-height: 72px;
}

:not(.bi) {
  font-family: 'Pretendard', 'Courier New', Courier, monospace;
}

html,
body {
  margin: 0;
  padding: 0;
  width: 100%;
  height: 100%;
}

header {
  position: relative;
  z-index: 5000;
}

.dropdown-menu {
  overflow: hidden;
}

#app {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100vh;
}

#content {
  position: relative;
  margin-top: calc(var(--nb-height) + 1.8rem);
  width: 100%;
  height: 100%;
}

.search-mode-background {
  cursor: pointer;

  position: absolute;
  z-index: 9999;

  background: black;

  visibility: hidden;
  opacity: 0;

  width: 100%;
  height: 100%;

  transition: opacity 0.6s ease-out, visibility 0.6s;
}

.search-mode ~ * > .search-mode-background {
  visibility: visible;
  opacity: 0.6;
}
</style>
