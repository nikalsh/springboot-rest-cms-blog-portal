import axios from "axios";

export default class UploadAdapter {
  constructor(loader) {
    this.loader = loader;
  }

  upload() {
    return this.loader.file
      .then(uploadedFile => {
        return new Promise((resolve, reject) => {
          let image = new FormData();
          let url = 'http://localhost:8080/uploadImage';
          image.append('file', uploadedFile);
          console.log(image);
          axios.post(url, image, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
            .then(response => {
              //console.log(response);
               // if (true) {
                resolve({
                  default: response.data,
                });
              // } else {
              //   reject(response.data.error.message);
              // }
            }).catch(error => {
            console.log(error);
          });
        });
      });

  }
}
