export const formatDate = (epochMillis: any) => {
  const date = new Date(epochMillis);
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  const month = (1 + date.getMonth()).toString().padStart(2, '0');
  return `${hours}:${minutes} ${day}.${month}`;
}
