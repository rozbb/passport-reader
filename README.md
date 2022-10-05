# e-Passport NFC Dumper

This app is a companion to the [zk-creds](https://github.com/rozbb/zkcreds-rs) anonymous credentials framework. This app dumps the contents of an NFC-equipped passport to a JSON blob. That JSON can then be used to create an anonymous credential in the zk-creds scheme. Prebuilt APKs can be found in the root directory of this repo.

## How to use

* Open the app and enter in the requested passport information. This is necessary because the data on the passport's NFC chip is encrypted using this info.
* Place your device on top of your passport. You may need to move it around to get it to work. It will vibrate when it finds the chip.
* Keep the device in place until reading is done.
* Once reading is done, you will be prompted to share a large JSON blob of text. Share this with yourself using whichever secure means you prefer (e.g., Signal's Note to Self feature)

## Thanks

This app is only possible thanks to Anton Tananaev's work. Original repo is [here](https://github.com/tananaev/passport-reader).

## Dependencies

Note that the app includes following third party dependencies:

- JMRTD - [LGPL 3.0 License](https://www.gnu.org/licenses/lgpl-3.0.en.html)
- SCUBA (Smart Card Utils) - [LGPL 3.0 License](https://www.gnu.org/licenses/lgpl-3.0.en.html)
- Spongy Castle - MIT-based [Bouncy Castle Licence](https://www.bouncycastle.org/licence.html)
- JP2 for Android - [BSD 2-Clause License](https://opensource.org/licenses/BSD-2-Clause)
- JNBIS - [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0)
- Material DateTimepicker - [Apache 2.0 License](https://www.apache.org/licenses/LICENSE-2.0)

## LGPL Disclosures

### JMRTD

The JMRTD library, which is vendored in `jmrtd/`, has been modified from its [original code](https://jmrtd.org/).

**Modifications:**

* `lds/SODFile.java` has a new public method `SODFile::getPreEContent`

## License

    Apache License, Version 2.0

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
