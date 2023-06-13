<script setup lang = "ts">
import {ref} from "vue";
import {Notice} from '../types';
const notices = ref<Notice[] | null>(null);
import EmbeddedNotice from '@/components/EmbeddedNotice.vue';
async function getNotice(){
    const resp = await fetch(`/notices`);
    if (!resp.ok) {
        return;
    }

    notices.value = await resp.json();
    console.log(notices)
}

getNotice()
</script>
<template>
    <table class="table">
        <tbody>
        <tr v-for="(item, index) in notices" :key="index" class="table-row">
            <embedded-notice :notice="item"></embedded-notice>
        </tr>
        </tbody>
    </table>
</template>



<style scoped>
.table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 20px;
}



.table-row:nth-child(even) {
    background-color: #f9f9f9;
}


</style>