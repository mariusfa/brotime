const HOUR_MILLIS = 3600_000;

export const formatDate = (epochMillis: any) => {
  const date = new Date(epochMillis);
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  const month = (1 + date.getMonth()).toString().padStart(2, '0');
  return `${hours}:${minutes} ${day}.${month}`;
}

export const formatTime = (millisDiff: any) => {
    const hoursAndDecimals = Math.abs(millisDiff / HOUR_MILLIS)
    const hours = Math.floor(hoursAndDecimals);
    const minutes = Math.floor((hoursAndDecimals - hoursAndDecimals)*60)
    const prefixSymbol = (millisDiff < 0) ? '- ' : '';
    return `${prefixSymbol}${hours}h ${minutes}m`;
}
