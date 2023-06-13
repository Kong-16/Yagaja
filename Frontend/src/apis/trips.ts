export interface TripSearchQuery {
  keyword?: string;
  contentTypeId?: number;
  areaCode?: number;
  regionCode?: number;
}

export interface TripDto {
  contentId: number;
  contentTypeId: number;
  title: string;
  address: string;
  zipcode: string;
  tel: string;
  thumbnail: string;
  readCount: number;
  areaCode: number;
  regionCode: number;
  latitude: number;
  longitude: number;
}

export async function fetchTrips(tripSearchQuery: TripSearchQuery): Promise<TripDto[]> {
  const query = Object.entries(tripSearchQuery)
    .map(([k, v]) => `${k}=${v}`)
    .join('&');
  const resp = await fetch('/attractions' + (query ? `?${query}` : ''));
  return resp.ok ? [...(await resp.json())] : [];
}
