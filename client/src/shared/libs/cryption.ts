import CryptoJS from 'crypto-js';

export const enCryption = (galleryId: number | string) => {
  return CryptoJS.AES.encrypt(
    JSON.stringify(galleryId),
    process.env.REACT_APP_CRYPTION_SALT!,
  );
};

export const deCryption = (galleryId: string) => {
  let decryption = CryptoJS.AES.decrypt(
    galleryId,
    process.env.REACT_APP_CRYPTION_SALT!,
  );
  return parseInt(decryption.toString(CryptoJS.enc.Utf8));
};
