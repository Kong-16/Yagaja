<script>
import 'leaflet-routing-machine/dist/leaflet-routing-machine.css';
import L from 'leaflet';
import { IRouter, IGeocoder, LineOptions } from 'leaflet-routing-machine';
import { isProxy, toRaw } from 'vue';

const props = {
  mapObject: {
    type: Object,
  },
  visible: {
    type: Boolean,
    default: true,
  },
  waypoints: {
    type: Array,
    required: true,
  },
  router: {
    type: IRouter,
  },
  plan: {
    type: L.Routing.Plan,
  },
  geocoder: {
    type: IGeocoder,
  },
  fitSelectedRoutes: {
    type: [String, Boolean],
    default: 'smart',
  },
  lineOptions: {
    type: LineOptions,
  },
  routeLine: {
    type: Function,
  },
  autoRoute: {
    type: Boolean,
    default: true,
  },
  routeWhileDragging: {
    type: Boolean,
    default: false,
  },
  routeDragInterval: {
    type: Number,
    default: 500,
  },
  waypointMode: {
    type: String,
    default: 'connect',
  },
  useZoomParameter: {
    type: Boolean,
    default: false,
  },
  showAlternatives: {
    type: Boolean,
    default: false,
  },
  altLineOptions: {
    type: LineOptions,
  },
  draggableWaypoints: {
    type: Boolean,
    default: false,
  },
};

export default {
  props,
  name: 'LRoutingMachine',
  data() {
    return {
      ready: false,
      layer: null,
    };
  },
  watch: {
    mapObject() {
      if (this.mapObject === null) {
        if (this.layer !== null) {
          this.layer = null;
        }
        return;
      }
      this.add();
    },
    waypoints() {
      if (this.mapObject === null) {
        if (this.layer !== null) {
          this.layer = null;
        }
        return;
      }
      if (this.$props.visible) {
        if (this.layer === null) {
          this.add();
        } else {
          this.layer.getPlan().setWaypoints(this.$props.waypoints);
        }
      } else {
          this.layer.getPlan().setWaypoints([]);
      }
    },
    visible() {
      if (this.layer !== null) {
        console.log(this.$props.visible);
        console.log(this.$props.waypoints);
        console.log(this.layer);
        this.layer.getPlan().setWaypoints(this.$props.visible ? this.$props.waypoints : []);
      }
    },
  },
  mounted() {
    this.add();
  },
  beforeUnmount() {
    if (this.layer && this.mapObject !== null) {
      this.layer.remove();
      this.layer = null;
    }
  },
  methods: {
    add() {
      if (!this.mapObject) {
        return;
      }

      console.log(this.mapObject);
      if (this.layer !== null) {
        return;
      }

      const {
        waypoints,
        fitSelectedRoutes,
        autoRoute,
        routeWhileDragging,
        routeDragInterval,
        waypointMode,
        useZoomParameter,
        showAlternatives,
        draggableWaypoints,
        lineOptions,
      } = this;

      const options = {
        waypoints,
        fitSelectedRoutes,
        autoRoute,
        routeWhileDragging,
        routeDragInterval,
        waypointMode,
        useZoomParameter,
        showAlternatives,
        draggableWaypoints,
        lineOptions,
        createMarker: () => null,
      };

      const routingLayer = L.Routing.control(options);
      console.log(isProxy(this.mapObject));
      routingLayer.addTo(toRaw(this.mapObject));
      this.layer = routingLayer;
      this.ready = true;
    },
  },
};
</script>
