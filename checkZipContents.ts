import * as fs from 'fs';
import * as https from 'https';
import { execSync } from 'child_process';

const url = 'https://codeload.github.com/wahyu14app/sketchware-pro-ide/zip/refs/heads/main';
const dest = './test.zip';

async function download(): Promise<boolean> {
    return new Promise((resolve) => {
        const file = fs.createWriteStream(dest);
        https.get(url, (response) => {
            if (response.statusCode === 301 || response.statusCode === 302) {
                const redirectUrl = response.headers.location;
                if (redirectUrl) {
                    file.close();
                    fs.unlinkSync(dest);
                    https.get(redirectUrl, (r2) => {
                        const file2 = fs.createWriteStream(dest);
                        r2.pipe(file2);
                        file2.on('finish', () => { file2.close(); resolve(true); });
                    });
                }
            } else if (response.statusCode === 200) {
                response.pipe(file);
                file.on('finish', () => { file.close(); resolve(true); });
            } else {
                resolve(false);
            }
        });
    });
}

async function main() {
    console.log("Downloading zip...");
    const ok = await download();
    if (ok) {
        console.log("Downloaded. Listing all java/kt files in mod subfolders:");
        const list = execSync('unzip -l test.zip').toString();
        const lines = list.split('\n').filter(line => line.includes('/java/mod/') || line.includes('/kotlin/mod/'));
        console.log(`Found ${lines.length} mod java/kt files.`);
        
        // Print files related to compiler/build
        const buildLines = lines.filter(line => line.toLowerCase().includes('compiler') || line.toLowerCase().includes('builder') || line.toLowerCase().includes('build'));
        console.log("Compile/Build related files in mod directory:");
        console.log(buildLines);
        
        fs.unlinkSync(dest);
    } else {
        console.log("Failed.");
    }
}

main();
