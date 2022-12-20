<#import "commons.ftlh" as c>
<#import "/spring.ftl" as spring>

<#macro error field>
    <@spring.bind field/>
    <#if spring.status.errorMessage??>
        <div class="error">
            ${spring.status.errorMessage}
        </div>
    </#if>
</#macro>

<@c.page>
    <div class="form-box">
        <div class="header">Register</div>
        <div class="body">
            <form class="register-form" method="post" action="">
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" value="${registerForm.login!}"/>
                    </div>
                    <@error "registerForm.login"/>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input id="password" name="password" type="password"/>
                    </div>
                    <@error "registerForm.password"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Register">
                </div>
            </form>
        </div>
    </div>


</@c.page>
