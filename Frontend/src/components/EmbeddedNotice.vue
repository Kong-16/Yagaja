<script setup lang="ts">
import {defineProps} from "vue/dist/vue";
import {Notice} from "@/types";

const props = defineProps<{
    notice : Notice
}>();

</script>
<template>
  <div>
      {{props.notice.title}}
      {{props.notice.content}}
  </div>
</template>