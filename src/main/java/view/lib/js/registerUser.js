function addNewUserToDb() {
  var xhr = new XMLHttpRequest();
  xhr.open('POST', '/register', 'false');
  xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.onload = function() {
    if(xhr.status === 200) {
        alert(xhr.status);
    } else {
      alert(xhr.status);
    }
  }

  var email = $('#exampleInputEmail2').val();
  var pass = $('#exampleInputPassword2').val();
  var json = JSON.stringify({email: email, pass: pass});

  xhr.send(json);
}

// TODO Add condition to verify whether user was created with no error
function changePageView() {
  registerForm.style.display = 'none';
  authUser.style.display = 'block';
}

document.addEventListener("DOMContentLoaded", function(){
  window.registerForm = document.getElementById('registerForm');
  window.authUser = document.getElementById('authUser');

  var registerButton = document.getElementById('registerBtn');

  registerButton.addEventListener('click', function() {
    addNewUserToDb();
    changePageView();
  })