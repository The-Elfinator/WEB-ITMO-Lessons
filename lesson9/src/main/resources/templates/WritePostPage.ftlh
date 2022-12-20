<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="write-post form">
        <div class="header">Write Post</div>
        <div class="body">
            <form method="post" action="">
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title" value="${post.title!}"/>
                    </div>
                    <@c.error "post.title"/>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text">${post.text!}</textarea>
                    </div>
                    <@c.error "post.text"/>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Publish Post">
                </div>
            </form>
        </div>
    </div>
</@c.page>
