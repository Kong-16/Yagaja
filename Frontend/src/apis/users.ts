export interface UserDto {
  id: string;
  password: string;
  name: string;
  emailId: string;
  emailDomain: string;
  role: string;
}

export async function fetchUser(userId: string): Promise<UserDto | null> {
  const resp = await fetch('/users/' + userId);
  return resp.ok ? resp.json() : null;
}
