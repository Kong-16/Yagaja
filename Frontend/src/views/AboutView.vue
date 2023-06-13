<template>
    <div class="map-wrapper">
        <LMap ref="map" v-model:zoom="zoom" :center="[44.48865, 11.3317]">
            <LTileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" layer-type="base" name="OpenStreetMap"></LTileLayer>
        </LMap>
    </div>
</template>

<script setup lang="ts">
import { LMap, LTileLayer } from '@vue-leaflet/vue-leaflet';
import { ref } from 'vue';

const zoom = ref(11);
</script>
