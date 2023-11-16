const REGEX = new RegExp(/\$\{.*?\}/g);

/**
 * the function renders an object to a tagged string and performs token substitution
 * @param {object} input - a javascript object representing a hierachycal structure
 * @param {object} values - a list of key value pairs where the key is a token to be replaced with the value in strings present in input
 */
function render(input, values) {
  if (typeof input !== 'object' || typeof values !== 'object')
    throw new Error('InvalidType');
  if (Object.keys(input).length === 0) return '';
  return recursiveClosure(values)(input);
}

function recursiveClosure(values) {
  function recursive(input) {
    let result = '';
    Object.keys(input).forEach((i) => {
      result += `<${i}>${
        typeof input[i] !== 'object' ? findAndReplace(input[i], values) : recursive(input[i])
      }</${i}>`;
    });
    return result;
  }
  return recursive;
}

const findAndReplace = (str, values) => {
  if (!REGEX.test(str)) return str;
  const whatToSearch = str.match(REGEX).map((match) => {
    return match.replace('$', '').replace('{', '').replace('}', '');
  });
  if (!whatToSearch.some((crt) => values[crt])) return str;
  let result = str;
  whatToSearch.forEach((crt) => {
    result = result.replace(`$\{${crt}}`, values[crt]);
  });
  return result;
};

module.exports = {
  render,
};