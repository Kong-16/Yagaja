<script setup lang="ts">
import SimpleCardViewLayout from '@/components/SimpleCardViewTemplate.vue';
import { onBeforeMount } from 'vue';
import { useRouter } from 'vue-router';
import { Authentication } from '../types';
import { signin } from '../apis/auth';

const $router = useRouter();

async function doSignin(e: Event) {
  const signinDto = Object.fromEntries(new FormData(e.target as HTMLFormElement).entries());
  const signinResult = await signin(signinDto as any);  // eslint-disable-line
  if (!signinResult.authenticated) {
    alert(signinResult.message);
    return;
  }

  const resp = await fetch('/auth/probe', { method: 'POST' });
  if (!resp.ok) {
    console.log('TODO(ALL): 로그인 예외 처리 하기');  // TODO(ALL): 로그인 예외 처리 하기
  }

  const json = await resp.json() as Authentication;
  if (!json.authenticated) {
    return;
  }

  self.dispatchEvent(new CustomEvent('authchange', { detail: json }));
  $router.back();
}

async function checkWhetherSignIn() {
  const resp = await fetch('/auth/probe', { method: 'POST' });
  if (!resp.ok) {
    return;
  }
  const auth = await resp.json() as Authentication;
  if (auth.authenticated) {
    self.dispatchEvent(new CustomEvent('authchange', { detail: auth }));
    $router.replace({ path: '/' });
  }
}

onBeforeMount(checkWhetherSignIn);
</script>

<template>
  <simple-card-view-layout>
    <h2 class="mb-4">로그인</h2>

    <form id="signin-form" class="row g-2" @submit.prevent="doSignin">
      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="text" class="form-control" name="username" placeholder="아이디" required />
          <label for="username">아이디</label>
        </div>
      </div>

      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="password" class="form-control" name="password" placeholder="비밀번호" required />
          <label for="password">비밀번호</label>
        </div>
      </div>

      <div class="col-12 pt-0 pb-4" style="color: lightslategrey; cursor: pointer">
        <a>비밀번호를 잊어버렸나요?</a>
      </div>

      <div class="col-12">
        <button type="submit" class="btn btn-outline-primary w-100">로그인</button>
      </div>
      <div class="col-12">
        <button type="button" class="btn btn-outline-info w-100" @click="$router.push({ name: 'signup' })">회원가입</button>
      </div>
    </form>
  </simple-card-view-layout>
</template>
