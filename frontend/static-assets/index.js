document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("long-url-form");

    form.addEventListener("submit", async (event) => {
        event.preventDefault(); // Prevent default form submission
        const inputData = document.getElementById("url").value;
        const resultContainer = document.getElementById("result-container");
        const shortUrlParagraph = document.getElementById("short-url");

        // Clear previous content
        shortUrlParagraph.innerHTML = "";
        resultContainer.style.display = "none";

        if (!inputData.trim()) {
            shortUrlParagraph.innerHTML = `<span style="color: red;">Please enter a valid URL.</span>`;
            resultContainer.style.display = "block";
            return;
        }

        // Construct JSON payload
        const payload = { url: inputData };

        const response = await fetch("http://localhost:8087/short-url", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)
        });

        const resultData = await response.json();

        if (!response.ok) {
            shortUrlParagraph.innerHTML = `<span style="color: darkred;">Error ${resultData.message || response.statusText}</span>`;
        } else {
            shortUrlParagraph.innerHTML = `Shortened URL: <a href="${resultData.shortUrl}" target="_blank">${resultData.shortUrl}</a>`;

            // Copy button functionality
            let copyButton = document.querySelector(".copy-button");
            if (!copyButton) {
                copyButton = document.createElement("button");
                copyButton.textContent = "Copy";
                copyButton.classList.add("copy-button");
                resultContainer.appendChild(copyButton);
            }

            copyButton.onclick = () => {
                navigator.clipboard.writeText(resultData.shortUrl).then(() => {
                    copyButton.textContent = "Copied!";
                    setTimeout(() => {
                        copyButton.textContent = "Copy";
                    }, 2000);
                });
            };
        }

        // Show the result container
        resultContainer.style.display = "block";
    });
});
