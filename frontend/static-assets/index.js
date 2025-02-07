document.addEventListener("DOMContentLoaded", () => {
    const button = document.getElementById("long-url-form");

    button.addEventListener("submit", async (event) => { // Pass `event` parameter
        event.preventDefault();
        const inputData = document.getElementById("url").value;

        // Construct JSON payload
        const payload = { url: inputData };

        const response = await fetch("http://localhost:8087/short-url", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(payload)  // Send JSON payload
        });

        const data = await response.json(); // Parse JSON response
        console.log("Shortened URL:", data.shortUrl);

        // Display the shortened URL inside the result-container
        const resultContainer = document.getElementById("result-container");
        const shortUrlParagraph = document.getElementById("short-url");

        // Show the container
        resultContainer.style.display = "block";

        // Set the shortened URL as a clickable link
        shortUrlParagraph.innerHTML = `Shortened URL: <a href="${data.shortUrl}" target="_blank">${data.shortUrl}</a>`;

        // Log the link to the console to verify if it's correct
        console.log("Link is correctly set to:", data.shortUrl);

        if (!document.querySelector(".copy-button")) {
            const copyButton = document.createElement("button");
            copyButton.textContent = "Copy";
            copyButton.classList.add("copy-button");
            resultContainer.appendChild(copyButton);

            // Add an event listener for the "Copy URL" button
            copyButton.addEventListener("click", () => {
                // Copy the shortened URL to the clipboard
                navigator.clipboard.writeText(data.shortUrl).then(() => {
                    copyButton.textContent = "Copied!"; // Change button text
                    setTimeout(() => {
                        copyButton.textContent = "Copy"; // Reset text after 2 seconds
                    }, 2000);
                });
            });
        }
    });
});
