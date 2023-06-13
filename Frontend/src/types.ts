export interface TripInfo {
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

export interface TripReview {
    id: number;
    userId: string;
    name: string;
    contentId: number;
    title: string;
    content: string;
    createdDate: Date;
    modifiedDate: Date;
}
export interface Plan {
    planId : number;
    userId : string;
    name : string;
    dsescription : string;
    waypoints : string;
    attractionList : object;
}

export interface Notice{
    id : number;
    authorId : string;
    authorName : string;
    title : string;
    content : string;
    createTime : Date;
}

export interface Notice{
    id : number;
    authorId : string;
    authorName : string;
    title : string;
    content : string;
    createTime : Date;
}
