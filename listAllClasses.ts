import { execSync } from 'child_process';
import * as fs from 'fs';
import * as path from 'path';
import * as os from 'os';

function searchDirectory(dir: string, results: string[]) {
    if (!fs.existsSync(dir)) return;
    try {
        const files = fs.readdirSync(dir);
        for (const file of files) {
            const fullPath = path.join(dir, file);
            let stat;
            try {
                stat = fs.statSync(fullPath);
            } catch (e) {
                continue;
            }
            if (stat.isDirectory()) {
                if (file !== '.git' && file !== '.gradle' && file !== 'build' && file !== '.next') {
                    searchDirectory(fullPath, results);
                }
            } else if (file.endsWith('.jar') || file.endsWith('.aar')) {
                results.push(fullPath);
            }
        }
    } catch (e) {}
}

const jarFiles: string[] = [];
console.log("Searching for all JAR/AAR files in the project and home directory...");
searchDirectory('./app', jarFiles);
searchDirectory(path.join(os.homedir(), '.gradle'), jarFiles);

console.log(`Found ${jarFiles.length} jar/aar files. Scanning contents...`);

for (const jarPath of jarFiles) {
    try {
        let list = "";
        if (jarPath.endsWith('.jar')) {
            list = execSync(`unzip -l "${jarPath}"`).toString();
        } else { // aar
            list = execSync(`unzip -p "${jarPath}" classes.jar 2>/dev/null | unzip -l /dev/stdin 2>/dev/null || unzip -l "${jarPath}"`).toString();
        }
        const matches = list.split('\n').filter(line => 
            line.toLowerCase().includes('buildsettings') || 
            line.toLowerCase().includes('builtinlibraries') || 
            line.toLowerCase().includes('buildprogressreceiver')
        );
        if (matches.length > 0) {
            console.log(`\nMATCH FOUND in: ${jarPath}`);
            console.log(matches);
        }
    } catch (e) {}
}
console.log("\nScan complete.");
