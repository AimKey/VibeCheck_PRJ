<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Upload Failed</title>
    <style>
        .popup {
            display: flex;
            justify-content: center;
            align-items: center;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
        }
        .popup-content {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            text-align: center;
        }
        .popup-content h2 {
            color: red;
        }
        .popup-content button {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: red;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="popup">
        <div class="popup-content">
            <h2>Upload Failed!</h2>
            <p>There was an error uploading your song. Please try again.</p>
<!--            IDK please do this for me-->
            <button onclick="window.location.href='???'">OK</button>

        </div>
    </div>
</body>
</html>
