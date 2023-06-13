export interface Authentication {
  authenticated: boolean;
  userInfo: {
    id: string;
    name: string;
  } | null;
}

export async function probe(): Promise<Authentication | null> {
  const resp = await fetch('/auth/probe', { method: 'POST' });
  return resp.ok ? await resp.json() : null;
}

export async function signin(credentials: {
  username: string;
  password: string;
}): Promise<{ authenticated: boolean; message: string }> {
  if (!('username' in credentials) || !credentials.username) {
    return { authenticated: false, message: '아이디를 입력하세요.' };
  }
  if (!('password' in credentials) || !credentials.password) {
    return { authenticated: false, message: '비밀번호를 입력하세요.' };
  }

  const resp = await fetch('/auth/signin', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
    },
    body: new URLSearchParams(credentials),
  });
  if (!resp.ok) {
    return { authenticated: false, message: '서버에서 알 수 없는 오류가 발생하여 요청을 실패하였습니다.' };
  }

  return await resp.json();
}

export async function signout(): Promise<boolean> {
  // TODO(ALL): 로그아웃 예외 처리 해야함
  const resp = await fetch('/auth/signout', { method: 'POST' });
  self.dispatchEvent(new CustomEvent('authchange', { detail: null }));
  return resp.ok;
}
