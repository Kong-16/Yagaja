<template>
  <div class="card-view-container container-xxl">
    <section class="card-view-content-wrapper">
      <slot></slot>
    </section>
  </div>
</template>

<style scoped>
.card-view-container {
  display: flex;
  justify-content: center;
  padding: 1.2em;
}

.card-view-content-wrapper {
  max-width: 520px;
  padding: 1.6em;
  border-radius: 1rem;
  box-shadow: var(--color-gray-0) 0 0 11px 1px;
  width: 100%;
}
</style>
