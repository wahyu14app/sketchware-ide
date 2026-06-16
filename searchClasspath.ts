import { execSync } from 'child_process';
import * as fs from 'fs';
import * as path from 'path';
import * as os from 'os';

function searchDir(dir: string) {
    if (!fs.existsSync(dir)) return;
    const files = fs.readdirSync(dir);
    for (const file of files) {
        const fullPath = path.join(dir, file);
        const stat = fs.statSync(fullPath);
        if (stat.isDirectory()) {
            searchDir(fullPath);
        } else if (file.endsWith('.jar')) {
            try {
                const list = execSync(`jar tf "${fullPath}"`).toString();
                if (list.includes('BuildSettings') || list.includes('BuiltInLibraries')) {
                    console.log(`FOUND in cached jar: ${fullPath}`);
                    const lines = list.split('\n').filter(line => line.includes('BuildSettings') || line.includes('BuiltInLibraries'));
                    console.log(lines);
                }
            } catch (e) {}
        }
    }
}

console.log("Searching Gradle cache...");
const gradleDir = path.join(os.homedir(), '.gradle', 'caches');
searchDir(gradleDir);
console.log("Search finished.");
