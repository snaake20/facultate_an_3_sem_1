/**
 * @param {string} a
 */
const decompressHelperFn = (a) => {
  let result = '';
  a.split('').forEach((char, idx, a) => {
    if (!isNaN(parseInt(char))) {
      return;
    }
    result += char.repeat(parseInt(a[idx + 1]));
  });
  return result;
};

const reduceHelperFn = () => {
  let howManyTimes = 0;
  return (acc, curr, idx, a) => {
    if (acc && acc?.split('')?.at(-1) === curr) {
      // acc && acc?.split('')?.[acc.split('').length - 1] === curr for lower versions of node
      howManyTimes++;
    } else {
      howManyTimes = 1;
      acc += curr;
    }
    if (a?.[idx + 1] === undefined || curr !== a?.[idx + 1]) {
      acc += howManyTimes;
      howManyTimes = 0;
    }
    return acc;
  };
};

/**
 * @param {string} a
 *
 * @returns {string} compressed string
 */
const compressHelperFunctionalFn = (a) => {
  return a.split('').reduce(reduceHelperFn(), '');
};

/**
 * @param {string} a
 *
 * @returns {boolean}
 */
const isString = (a) => typeof a === 'string' || a instanceof String;

const compress = (a, b = true) => {
  if (isString(a) === false) {
    throw new Error('InvalidType');
  }
  if (typeof b !== 'boolean') {
    throw new Error('InvalidType');
  }
  if (a === '') return '';
  return b ? compressHelperFunctionalFn(a) : decompressHelperFn(a);
};

module.exports = compress;