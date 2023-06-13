import { TripDto } from "./trips";

export interface PlanDto {
  name: string;
  subplans: SubPlanDto[];
}

export interface SubPlanDto {
  name: string;
  waypoints: { index: number, trip: TripDto }[];
}
