import heic2any from 'heic2any';

export const ALLOW_FILE_EXTENSION = 'jpg, jpeg, png, heic';

export const uploadHelper = (img: File) => {
  const name = img.name;
  const size = img.size;

  if (size > 10 * 1024 * 1024) return false;

  const result = name.split('.').map((el) => el.toLowerCase());

  if (
    result[result.length - 1] &&
    ALLOW_FILE_EXTENSION.indexOf(result[result.length - 1]) > -1
  ) {
    return true;
  } else {
    return false;
  }
};

export const heicTojpeg = async (img: any) => {
  if (img?.type === 'image/heic' || img?.type === 'image/heif') {
    const name = img.name.split('.');
    name[name.length - 1] = '.jpg';
    const convertName = name.join('');

    // let resultBolb = await heic2any({ blob: img, toType: 'image/jpeg' })
    let res = heic2any({ blob: img, toType: 'image/jpeg' })
      .then(function (resultBlob) {
        //file에 새로운 파일 데이터를 씌웁니다.
        // @ts-ignore
        const file = new File([resultBlob], convertName, {
          type: 'image/jpeg',
          lastModified: new Date().getTime(),
        });
        return file;
      })
      .catch(function (err) {
        alert('이미지 변환 오류');
      });
    return res;
  } else return img;
};
