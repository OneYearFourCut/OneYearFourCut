export const ALLOW_FILE_EXTENSION = 'jpg, jpeg, png, heic';

export const uploadHelper = (img: File) => {
  const name = img.name;
  const size = img.size;

  if (size > 5 * 1024 * 1024) return false;

  const result = name.split('.').map((el) => el.toLowerCase());

  if (result[1] && ALLOW_FILE_EXTENSION.indexOf(result[1]) > -1) {
    return true;
  } else {
    return false;
  }
};