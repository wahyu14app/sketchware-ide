import { execSync } from 'child_process';
import * as fs from 'fs';
import * as path from 'path';

const libsDir = './app/libs';
const files = fs.readdirSync(libsDir);

for (const file of files) {
    if (file.endsWith('.jar')) {
        const jarPath = path.join(libsDir, file);
        try {
            const list = execSync(`jar tf ${jarPath}`).toString();
            if (list.includes('BuildSettings') || list.includes('BuiltInLibraries')) {
                console.log(`Found in: ${file}`);
                const lines = list.split('\n').filter(line => line.includes('BuildSettings') || line.includes('BuiltInLibraries'));
                console.log(lines);
            }
        } catch (e: any) {
            console.error(`Error reading ${file}:`, e.message);
        }
    }
}
