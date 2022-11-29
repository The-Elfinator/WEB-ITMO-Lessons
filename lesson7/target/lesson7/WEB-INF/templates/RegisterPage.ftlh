<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Register</div>
        <div class="body">
            <form class="register-form" method="post" action="">
                <input type="hidden" name="action" value="register"/>
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="email">Email</label>
                    </div>
                    <div class="value">
                        <input id="email" name="email"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input id="password" name="password" type="password"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="passwordConfirmation">Confirm password</label>
                    </div>
                    <div class="value">
                        <input id="passwordConfirmation" name="passwordConfirmation" type="password"/>
                    </div>
                </div>

                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Register">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".register-form").submit(function () {
                const $form = $(this);
                const login = $form.find("input[name='login']").val();
                const email = $form.find("input[name='email']").val();
                const password = $form.find("input[name='password']").val();
                const passwordConfirmation = $form.find("input[name='passwordConfirmation']").val();

                $.ajax({
                    method: "POST",
                    data: { action : "register", login, email, password, passwordConfirmation },
                    dataType: "json",
                    success: function (response) {
                        if (response["error"]) {
                            $form.find(".error").text(response["error"]);
                        } else if (response["redirect"]) {
                            location.href = response["redirect"];
                        }
                        return false;
                    }
                });

                return false;
            });
        });
    </script>
</@c.page>
