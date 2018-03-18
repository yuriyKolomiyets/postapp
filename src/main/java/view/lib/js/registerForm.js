function openRegister() {

  loginForm.style.display = 'none';
  registerForm.style.display = 'block';
}

function openLogin() {
  loginForm.style.display = 'block';
  registerForm.style.display = 'none';
}

document.addEventListener("DOMContentLoaded", function(){
  window.loginForm = document.getElementById('loginForm');
  window.registerForm = document.getElementById('registerForm');

  var registerButton = document.getElementById('toggleRegister');
  var backToLogin = document.getElementById('backToLogin')

  registerButton.addEventListener('click', function() {
    openRegister();
  })

  backToLogin.addEventListener('click', function() {
    openLogin();
  })
})