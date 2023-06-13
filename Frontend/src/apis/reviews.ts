export interface TripReviewDto {
  id: number;
  userId: string;
  contentId: number;
  title: string;
  content: string;
  createdDate: Date;
  modifiedDate: Date;
}

export async function fetchReviews(contentId: number): Promise<TripReviewDto[] | null> {
  const resp = await fetch(`/reviews/${contentId}`);
  return resp.ok ? await resp.json() : null;
}
