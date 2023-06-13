<script setup lang="ts">
import SimpleCardViewLayout from '@/components/SimpleCardViewTemplate.vue';
import { useRouter } from 'vue-router';

const router = useRouter();

async function signup(signupDto: { id: string, password: string, name: string, emailId: string, emailDomain: string }) {
  const resp = await fetch('/users/join', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json;charset=utf-8',
    },
    body: JSON.stringify(signupDto),
  });
  if (resp.status !== 201) {
    alert('회원가입에 실패하였습니다.');
  } else {
    alert('회원가입에 성공하였습니다.\n로그인해주세요 !');
    router.replace({ name: 'login' });
  }
}

function validateAndSignup(e: Event) {
  const formEl = e.target as HTMLFormElement;
  formEl.classList.add('was-validated');

  const signupDto = Object.fromEntries(new FormData(e.target as HTMLFormElement).entries()) as any;  // eslint-disable-line
  if (signupDto.password !== signupDto.passwordVerify) {
    formEl.passwordVerify.classList.add('is-invalid');
    formEl.passwordVerify.value = '';
    return;
  } else {
    formEl.passwordVerify.classList.remove('is-invalid');
    delete signupDto.passwordVerify;
  }

  if (!/[A-Za-z0-9]+@[^.]+\.[^.]+/.test(signupDto.email)) {
    formEl.email.classList.add('is-invalid');
    formEl.email.value = '';
    return;
  } else {
    formEl.email.classList.remove('is-invalid');
    signupDto.emailId = signupDto.email.split('@')[0];
    signupDto.emailDomain = signupDto.email.split('@')[1];
    delete signupDto.email;
  }

  signup(signupDto);  // eslint-disable-line
}
</script>

<template>
  <simple-card-view-layout>
    <h2 class="mb-4">회원가입</h2>

    <form id="signin-form" class="row g-2" @submit.prevent="validateAndSignup($event)">
      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="text" class="form-control" name="id" placeholder="아이디" required />
          <label for="username">아이디</label>
        </div>
      </div>

      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="password" class="form-control" name="password" placeholder="비밀번호" required />
          <label for="password">비밀번호</label>
        </div>
      </div>

      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="password" class="form-control" id="passwordVerify" name="passwordVerify" placeholder="비밀번호 재확인"
            required />
          <label for="passwordVerify">비밀번호 재확인</label>
          <div class="invalid-feedback">
            입력한 비밀번호와 일치하지 않습니다.
          </div>
        </div>
      </div>


      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="text" class="form-control" name="name" placeholder="이름" required />
          <label for="password-verify">이름</label>
        </div>
      </div>

      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="text" class="form-control" name="email" placeholder="E-mail" required />
          <label for="email">E-mail</label>
          <div class="invalid-feedback">
            올바른 이메일 주소를 입력해주세요.
          </div>
        </div>
      </div>

      <div class="col-12">
        <button type="submit" class="btn btn-outline-primary w-100">회원가입</button>
      </div>
      <div class="col-12">
        <button type="button" class="btn btn-outline-secondary w-100" @click="$router.back()">돌아가기</button>
      </div>
    </form>
  </simple-card-view-layout>
</template>
