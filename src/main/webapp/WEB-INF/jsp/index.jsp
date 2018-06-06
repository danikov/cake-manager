<%@ page import="java.util.Enumeration" %>
<%@ page import="com.waracle.cakemgr.Cake" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="java.util.Collections" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Cake Application</title>
</head>
<style>
    .container {
        display: inline-block;
        position: relative;
        max-width: 20%;
        height: auto;
        margin: auto 7px;
    }

    .image {
        opacity: 1;
        display: block;
        height: auto;
        transition: .5s ease;
        backface-visibility: hidden;
    }

    .middle {
        transition: .5s ease;
        opacity: 0;
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        -ms-transform: translate(-50%, -50%);
        text-align: center;
    }

    .caption {
        display: block;
        margin-bottom: 7px;
    }

    .container:hover .image {
        opacity: 0.2;
    }

    .container:hover .middle {
        opacity: 1;
    }
</style>
<body>
<div>
    <h3>Current cakes:</h3>
    <c:forEach var="cake" items="${cakes}">
        <figure class="container">
            <img src="<c:out value = "${cake.image}"/>" alt="<c:out value = "${cake.title}"/>" class="image"
                 style="width:100%">
            <div class="middle">
                <div class="text"><c:out value="${cake.description}"/></div>
            </div>
            <figcaption class="caption"><c:out value="${cake.title}"/></figcaption>
        </figure>
    </c:forEach>
    <h3>Add (or update) a cake:</h3>
    <div>${feedback}</div>
    <form action="/cakes" method="post">
        <div>
            <label for="title">Title</label>
            <div>
                <input name="title" id="title"/>
            </div>
        </div>
        <div>
            <label for="desc">Description</label>
            <div>
                <input name="desc" id="desc"/>
            </div>
        </div>
        <div>
            <label for="image">Image Url</label>
            <div>
                <input name="image" id="image"/>
            </div>
        </div>
        <div>
            <div>
                <button type="submit">Submit</button>
            </div>
        </div>
    </form>
</div>
</body>
</html>
