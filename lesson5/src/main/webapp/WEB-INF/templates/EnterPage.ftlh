<#import "./common.ftlh" as c>

<@c.page>
    <div class="form-box" style="margin-top: 5rem; margin-bottom: 5rem">
        <div class="header">Enter into Codeforces</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="enter">
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" value="${login!}"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input id="password" type="password" name="password"/>
                    </div>
                </div>
                <#if error??>
                    <div class="error">
                        ${error}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" value="Enter">
                </div>
            </form>
        </div>
    </div>
</@c.page>