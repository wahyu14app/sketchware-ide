import * as fs from 'fs';
import * as path from 'path';

function search(dir: string) {
    if (!fs.existsSync(dir)) return;
    const files = fs.readdirSync(dir);
    for (const file of files) {
        const fullPath = path.join(dir, file);
        const stat = fs.statSync(fullPath);
        if (stat.isDirectory()) {
            if (file !== '.git' && file !== '.gradle' && file !== 'build' && file !== '.next') {
                search(fullPath);
            }
        } else {
            if (file.includes('BuildSettings') || file.includes('BuiltInLibraries')) {
                console.log(`FOUND FILENAME MATCH: ${fullPath}`);
            }
        }
    }
}

console.log("Searching file names in workspace...");
search('.');
console.log("Finished workspace search.");
