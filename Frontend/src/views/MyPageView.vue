<script setup lang="ts">
import SimpleCardViewTemplate from '@/components/SimpleCardViewTemplate.vue';
import { fetchUser, UserDto } from '../apis/users';
import { probe } from '../apis/auth';
import { ref, watchEffect } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { computed } from '@vue/reactivity';

const route = useRoute();
const router = useRouter();
const userInfo = ref<UserDto | null>(null);
const userEmail = computed(() => `${userInfo.value?.emailId}@${userInfo.value?.emailDomain}`);

function validateAndUpdate(e: Event) {
  const formEl = e.target as HTMLFormElement;
  formEl.classList.remove('was-validated');

  if (formEl.password.value !== userInfo.value?.password) {
    formEl.password.classList.add('is-invalid');
    return;
  } else {
    formEl.password.classList.remove('is-invalid');
  }

  if (0 < formEl.newPassword.value.length) {
    if (formEl.newPassword.value !== formEl.newPasswordVerify.value) {
      formEl.newPasswordVerify.classList.add('is-invalid');
      return;
    } else {
      formEl.newPasswordVerify.classList.remove('is-invalid');
    }
  }
  if (!/[A-Za-z0-9]+@[^.]+\.[^.]+/.test(formEl.email.value)) {
    formEl.email.classList.add('is-invalid');
    formEl.email.value = '';
    return;
  } else {
    formEl.email.classList.remove('is-invalid');
  }

  update(formEl);
}
async function update(formEl: HTMLFormElement) {
  if (userInfo.value !== null) {
    const resp = await fetch(`/users/${userInfo.value.id}`, {
      method: 'PUT',
      headers: {
        'Content-type': 'application/json;charset=utf-8',
      },
      body: JSON.stringify({
        password: formEl.newPassword.value,
        name: (formEl.querySelector('[name="name"]') as HTMLInputElement).value, // eslint-disable-line
        emailId: formEl.email.value.split('@')[0],
        emailDomain: formEl.email.value.split('@')[1],
      }),
    });
    if (resp.ok) {
      alert('회원 정보가 수정되었습니다.');
    } else {
      alert('회원 정보 수정을 실패하였습니다.');
    }
  }
}

watchEffect(async () => {
  const auth = await probe();
  if (!auth?.authenticated || route.params.userId !== auth?.userInfo?.id) {
    alert('접근 권한이 없습니다.');
    router.replace({ path: '/' });
    return;
  }

  if (auth?.userInfo) {
    userInfo.value = await fetchUser(auth.userInfo.id);
  }
});
async function deleteUser() {
  if (userInfo.value !== null) {
    const resp = await fetch(`/users/${userInfo.value?.id}`, {
      method: 'DELETE',
    });
    if (resp.ok) {
      self.dispatchEvent(new CustomEvent('authchange', { detail: { authenticated: false, userInfo: null } }));
      alert('그동안 서비스를 이용해주셔서 감사합니다.');
      router.replace({ path: '/' });
    } else {
      alert('알 수 없는 오류로 요청을 실패하였습니다.');
    }
  }
}
</script>

<template>
  <simple-card-view-template>
    <h2 class="mb-4">회원 정보 수정</h2>

    <form id="signin-form" class="row g-2" @submit.prevent="validateAndUpdate">
      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="text" class="form-control" name="id" placeholder="아이디" :value="userInfo?.id" disabled />
          <label for="id">아이디</label>
        </div>
      </div>

      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="password" class="form-control" name="password" placeholder="현재 비밀번호" required />
          <label for="password">현재 비밀번호</label>
          <div class="invalid-feedback">현재 비밀번호와 일치하지 않습니다.</div>
        </div>
      </div>

      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="password" class="form-control" id="newPassword" name="newPassword" placeholder="새 비밀번호" />
          <label for="newPassword">새 비밀번호</label>
        </div>
      </div>

      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="password" class="form-control" id="newPasswordVerify" name="newPasswordVerify" placeholder="새 비밀번호 재확인" />
          <label for="newPasswordVerify">새 비밀번호 재확인</label>
          <div class="invalid-feedback">입력한 새로운 비밀번호와 일치하지 않습니다.</div>
        </div>
      </div>

      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="text" class="form-control" name="name" placeholder="이름" :value="userInfo?.id" required />
          <label for="name">이름</label>
        </div>
      </div>

      <div class="col-12">
        <div class="form-floating mb-2">
          <input type="text" class="form-control" name="email" placeholder="E-mail" :value="userEmail" required />
          <label for="email">E-mail</label>
          <div class="invalid-feedback">올바른 이메일 주소를 입력해주세요.</div>
        </div>
      </div>

      <div class="col-12">
        <button type="submit" class="btn btn-outline-primary w-100">수정하기</button>
      </div>
      <div class="col-12">
        <button type="button" class="btn btn-outline-secondary w-100" @click="$router.back()">돌아가기</button>
      </div>
      <div class="col-12">
        <button type="button" class="btn btn-outline-danger w-100" @click="deleteUser">회원탈퇴</button>
      </div>
    </form>
  </simple-card-view-template>
</template>
